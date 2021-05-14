module com.code.fauch.polyjuice {
    requires transitive java.desktop;
    exports com.code.fauch.polyjuice.api.types;
    exports com.code.fauch.polyjuice.api.types.converters;
    uses com.code.fauch.polyjuice.api.types.ITypeProvider;
    //provides com.code.fauch.polyjuice.spi.ITypeProvider with com.code.fauch.polyjuice.StdType;
}