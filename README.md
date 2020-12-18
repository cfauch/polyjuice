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

## How to serialize a simple parameter with polyjuice

First, instantiate a parameter.

* Use `Parameter.newConstant` to instantiate a constant parameter

```
final Parameter<Integer> p = Parameter.newConstant("p", StdType.INT, 42);
```

* Use `Parameter.newParameter` to instantiate a parameter or, if last parameter is true, to instantiate a constant.

```
final Parameter<Short> label = Parameter.newParameter("label", StdType.SHORT, null, false);
```
Next, call `getBytes` to encoded it into a sequence of bytes.

```
final byte[] buff = p.getBytes();
```

## How to serialize a custom object with polyjuice

First, define your custom object. Your object should extends `AbsContent`.

```
public final class ContentImpl extends AbsContent {

    public static final class Item extends AbsContent {
        
        private final Parameter<Short> size;
        private final Parameter<String> msg;
        private Item() {
            this.size = Parameter.newParameter("size", StdType.SHORT, null, false);
            this.msg = Parameter.newParameter("msg", StdType.STRING, null, false);
            this.addOrderedContents(Arrays.asList(this.size, this.msg));
        }
        
        public Item message(final String msg) {
            this.msg.setValue(msg);
            this.size.setValue((short)msg.length());
            return this;
        }
    }
    
    private final Parameter<Short> nb;    
    private final DynamicArray<Item> items; 
    
    public ContentImpl() {
        this.nb = Parameter.newParameter("nb", StdType.SHORT, (short)0, false);
        this.items = new DynamicArray<>();
        this.addOrderedContents(Arrays.asList(this.nb, this.items));
    }
    
    public Item newItem() {
        final Item item = new Item();
        this.items.add(item);
        this.nb.setValue((short) (this.nb.getValue() + 1));
        return item;
    }
}
```

Now encode it:

```
    final ContentImpl content = new ContentImpl();
    content.newItem().message("red");
    content.newItem().message("green");
    content.newItem().message("blue");
    final byte[] buff = content.getBytes();
```

## How to use dynamic object with polyjuice

Like `Parameter` and other custom objects that extends `AbsContent`, `DynamicArray` offer the ability to listen for each changes. Use it if you want to react on each changes over each items. If not, simply use a list.

Here is how you encode a `DynamicArray` of parameters that can grow up over the time.

```
   final DynamicArray&lt;Parameter&lt;?&gt;&gt; array = new DynamicArray&lt;&gt;();
   array.add(Parameter.newParameter("size", StdType.SHORT, (short)5, false));
   array.add(Parameter.newParameter("msg", StdType.STRING, "Hello", false));
   array.getBytes();
```
