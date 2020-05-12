module com.code.fauch.polyjuice {
    requires transitive java.desktop;
    exports com.code.fauch.polyjuice;
    exports com.code.fauch.polyjuice.spi;
    uses com.code.fauch.polyjuice.spi.IContentFactoryProvider;
    uses com.code.fauch.polyjuice.spi.ITypeProvider;
    provides com.code.fauch.polyjuice.spi.IContentFactoryProvider with com.code.fauch.polyjuice.StdContentFactory;
    provides com.code.fauch.polyjuice.spi.ITypeProvider with com.code.fauch.polyjuice.StdType;
}