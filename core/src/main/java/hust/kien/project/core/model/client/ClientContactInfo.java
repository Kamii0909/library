package hust.kien.project.core.model.client;

import jakarta.persistence.Embeddable;

@Embeddable
public class ClientContactInfo {
    private String name;
    private String address;


    @SuppressWarnings("all")
    public abstract static class ClientContactInfoBuilder<C extends ClientContactInfo, B extends ClientContactInfoBuilder<C, B>> {
        private String name;
        private String address;

        protected abstract B self();

        public abstract C build();

        public B name(final String name) {
            this.name = name;
            return self();
        }

        public B address(final String address) {
            this.address = address;
            return self();
        }

    }


    private static final class ClientContactInfoBuilderImpl
        extends ClientContactInfoBuilder<ClientContactInfo, ClientContactInfoBuilderImpl> {
        private ClientContactInfoBuilderImpl() {}

        @Override
        protected ClientContactInfoBuilderImpl self() {
            return this;
        }

        @Override
        public ClientContactInfo build() {
            return new ClientContactInfo(this);
        }
    }

    protected ClientContactInfo(final ClientContactInfoBuilder<?, ?> b) {
        this.name = b.name;
        this.address = b.address;
    }

    @SuppressWarnings("all")
    public static ClientContactInfoBuilder<?, ?> builder() {
        return new ClientContactInfoBuilderImpl();
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ClientContactInfo(name=" + this.getName() + ", address=" + this.getAddress() + ")";
    }

    public ClientContactInfo() {}

    public ClientContactInfo(final String name, final String address) {
        this.name = name;
        this.address = address;
    }
}
