# polyjuice
How to serialize data to transfer.

## Installation

If you use Maven add this dependency:

```
    <dependency>
      <groupId>com.fauch.code</groupId>
      <artifactId>polyjuice</artifactId>
      <version>1.0.0</version>
    </dependency>
```

Then if you want to use `snakeyaml` to describe your encoding with YAML files, add this dependency:

```
    <dependency>
      <groupId>org.yaml</groupId>
      <artifactId>snakeyaml</artifactId>
      <version>1.25</version>
    </dependency>
```

## How to serialize data with polyjuice

In this example we will show you how to build an object from a YAML file, modify it and encode it.

### 1. Create a `template.yml` with this content:

```
parameters:
-  &magical_number
   name: magicalNumber
   type: INT
   value: 42
   readonly: true
   
-  name: msgSize
   type: INT
   value: 13
   
-  name: msg
   type: STRING
   value: HELLO WORLD !
   
-  name: clock
   type: OFFSET_CLOCK
   value: PT-12H

-  *magical_number
```
With this file we indicate that we want to encode 5 parameters in this order:
1. An integer encoded with 4 bytes using big endian order.
2. An integer encoded with 4 bytes using big endian order.
3. A string encoded in UTF-8
4. A clock encoded with 6 x 4 bytes using beg endian order like this:

    1. The year as an integer encoded with 4 bytes
    2. The month as an integer encoded with 4 bytes
    3. The day as an integer encoded with 4 bytes
    4. The hour as an integer encoded with 4 bytes
    5. The minutes as an integer encoded with 4 bytes
    6. The seconds as an integer encoded with 4 bytes 

5. The same value as 1. encoded in the same way

It is possible to use all the types defined in the `StdType` class. It is also possible 
to define new ones using the Service Provider Interface.

### 2. Create the corresponding object

It must implement the `IObject` interface and extend the `AbsContent` class. It must
 also define a field by parameter present in the YAML file with the same name.

```
public class Truc extends AbsContent implements IObject {

    private Parameter<Integer> magicalNumber;
    
    private Parameter<Integer> msgSize;
    
    private Parameter<String> msg;
    
    private Parameter<OffsetClock> clock;

    public Parameter<Integer> getMagicalNumber() {
        return magicalNumber;
    }

    public void setMagicalNumber(Parameter<Integer> magicalNumber) {
        this.magicalNumber = magicalNumber;
    }

    public Parameter<Integer> getMsgSize() {
        return msgSize;
    }

    public void setMsgSize(Parameter<Integer> msgSize) {
        this.msgSize = msgSize;
    }

    public Parameter<String> getMsg() {
        return msg;
    }

    public void setMsg(Parameter<String> msg) {
        this.msg = msg;
    }

    public Parameter<OffsetClock> getClock() {
        return clock;
    }

    public void setClock(Parameter<OffsetClock> clock) {
        this.clock = clock;
    }

    @Override
    public void addOrderedParameters(final List<Parameter<?>> orderedParameters) {
        getOrderedParameters().addAll(orderedParameters);
    }

```
The `addOrderedParameters` method allows to specify the order of the parameters as they should be encoded in the sequence.
It is the same order as in the YAML file.

### 3. Now, use it
 
All you have to do is call `snakeyaml` with `ObjectFactory.class` and then call the `build()` method with the class of the object you have just defined. 

Don't forget to export the package of this class in your `mobile-info.java`, because `ObjectFactory` use reflection to instantiate corresponding object.

```
    final ObjectFactory payload = new Yaml(new Constructor(ObjectFactory.class)).load(in);
    final Truc truc = payload.build(Truc.class);
```

Once your object has been created, you can change its parameters...

```
    truc.getMsg().setValue("MERCY");
    truc.getMsgSize().setValue(5);
```

and then encode it....

```
    final byte[] encoded = truc.getBytes();
```

Here is an example:

```
    try (InputStream in = getClass().getResourceAsStream("/template.yml")) {
        final ObjectFactory payload = new Yaml(new Constructor(ObjectFactory.class)).load(in);
        final Truc truc = payload.build(Truc.class);
        truc.getMsg().setValue("MERCI");
        truc.getMsgSize().setValue(5);
        Assert.assertEquals(41, truc.getBytes().length);
    }
```

## What if you want an immutable object.

Imagine you want declare all your fields `final`. In this case you have to pass a builder to the `ObjectFactory.build()` method. Here is an example to read the same YAML file and create an immutable object.

### 1. Create the corresponding class

The `ImmutableTruc` class marks all of its fields as final and no longer implements `IObject`. Instead, a builder is defined in an inner class that implements `IObject`.

```
public final class ImmutableTruc extends AbsContent {

    private final Parameter<Integer> magicalNumber;
    
    private final Parameter<Integer> msgSize;
    
    private final Parameter<String> msg;
    
    private final Parameter<OffsetClock> clock;

    public static final class Builder extends AbsContent implements IObject {
        
        private Parameter<Integer> magicalNumber;
        
        private Parameter<Integer> msgSize;
        
        private Parameter<String> msg;
        
        private Parameter<OffsetClock> clock;
        
        public Parameter<Integer> getMagicalNumber() {
            return magicalNumber;
        }

        public void setMagicalNumber(Parameter<Integer> magicalNumber) {
            this.magicalNumber = magicalNumber;
        }

        public Parameter<Integer> getMsgSize() {
            return msgSize;
        }

        public void setMsgSize(Parameter<Integer> msgSize) {
            this.msgSize = msgSize;
        }

        public Parameter<String> getMsg() {
            return msg;
        }

        public void setMsg(Parameter<String> msg) {
            this.msg = msg;
        }

        public Parameter<OffsetClock> getClock() {
            return clock;
        }

        public void setClock(Parameter<OffsetClock> clock) {
            this.clock = clock;
        }

        public void addOrderedParameters(final List<Parameter<?>> orderedParameters) {
            getOrderedParameters().addAll(orderedParameters);
        }
        
        public ImmutableTruc build() {
            return new ImmutableTruc(this);
        }
    }
    
    private ImmutableTruc(final Builder builder) {
        super(Collections.unmodifiableList(new ArrayList<>(builder.getOrderedParameters())));
        this.clock = builder.clock;
        this.magicalNumber = builder.magicalNumber;
        this.msg = builder.msg;
        this.msgSize = builder.msgSize;
    }

    public Parameter<Integer> getMagicalNumber() {
        return magicalNumber;
    }

    public Parameter<Integer> getMsgSize() {
        return msgSize;
    }

    public Parameter<String> getMsg() {
        return msg;
    }

    public Parameter<OffsetClock> getClock() {
        return clock;
    }

}

```

### 2. Now, use it

Instead of calling the factory directly with our object class, we use the class of the builder.

```
    try (InputStream in = getClass().getResourceAsStream("template.yml")) {
        final ObjectFactory payload = new Yaml(new Constructor(ObjectFactory.class)).load(in);
        final ImmutableTruc truc = payload.build(ImmutableTruc.Builder.class).build();
        truc.getMsg().setValue("MERCI");
        truc.getMsgSize().setValue(5);
        Assert.assertEquals(41, truc.getBytes().length);
    }
```

## What about object with transient fields ?

Imagine you want to add a field `ip` in your object, but this field shouldn't be encoded.

### 1. Here is the new `template.yml`:

```
ip: 192.168.56.103
parameters:
-  &magical_number
   name: magicalNumber
   type: INT
   value: 42
   readonly: true
   
-  name: msgSize
   type: INT
   value: 13
   
-  name: msg
   type: STRING
   value: HELLO WORLD !
   
-  name: clock
   type: OFFSET_CLOCK
   value: PT-12H

-  *magical_number
```

### 2. Extend the `Truc` class to add the new field

```
public final class TrucWithIp extends Truc {

    private String ip;

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

}
```

### 3. Extend the `ObjectFactory` class to add the new field

Redefine the `build` method to initialize the `ip` field of the returned object.

```
public final class TrucWithIpFactory extends ObjectFactory {

    private String ip;

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }


    public TrucWithIp build() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
    NoSuchMethodException, SecurityException, IntrospectionException {
        final TrucWithIp truc = super.build(TrucWithIp.class);
        truc.setIp(this.ip);
        return truc;
    }
```

### 4. It's ready, you can use it

Instead of calling `snakeyaml` with `ObjectFactory.class`, you have to use the new one.

```
   try (InputStream in = getClass().getResourceAsStream("/truc-template-with-ip.yml")) {
        final TrucWithIpFactory payload = new Yaml(new Constructor(TrucWithIpFactory.class)).load(in);
        final TrucWithIp truc = payload.build();
        truc.getMsg().setValue("MERCI");
        truc.getMsgSize().setValue(5);
        Assert.assertEquals(41, truc.getBytes().length);
        Assert.assertEquals(Arrays.asList("msg", "msgSize"), labels);
        Assert.assertEquals("192.168.56.103", truc.getIp());
    }
```

## How to constrain the size of the encoded frame

Specify the size of the encoded result, it will be truncated or padded with zeros (if necessary).

### 1. Here is the new `template.yml`:

Note the new `size` transient field. 

```
size: 50
parameters:
-  &magical_number
   name: magicalNumber
   type: INT
   value: 42
   readonly: true
   
-  name: msgSize
   type: INT
   value: 13
   
-  name: msg
   type: STRING
   value: HELLO WORLD !
   
-  name: clock
   type: OFFSET_CLOCK
   value: PT-12H

-  *magical_number
```

### 2. Extend the `Truc` class

Create a class implementing `ISizedObject` with the expected size.

```
public final class TrucWithSize extends Truc implements ISizedObject {

    private Integer expectedSize;
    
    @Override
    public void setExpectedSize(final Integer size) {
        this.expectedSize = size;
    }

    @Override
    public Integer getExpectedSize() {
        return this.expectedSize;
    }
    
}
```

### 3. Now, use it

All you have to do is call `snakeyaml` with `SizedObjectFactory.class`.

```
    try (InputStream in = getClass().getResourceAsStream("/truc-template-with-size.yml")) {
        final SizedObjectFactory payload = new Yaml(new Constructor(SizedObjectFactory.class)).load(in);
        final TrucWithSize truc = payload.build(TrucWithSize.class);
        Assert.assertEquals(42, truc.getMagicalNumber().getValue().intValue());
        Assert.assertEquals(13, truc.getMsgSize().getValue().intValue());
        Assert.assertEquals("HELLO WORLD !", truc.getMsg().getValue());
        Assert.assertEquals(Duration.ofHours(-12), truc.getClock().getValue().getInformation());
        Assert.assertEquals(50, truc.getExpectedSize().intValue());
        Assert.assertEquals(50, truc.getBytes().length);
    }
```

