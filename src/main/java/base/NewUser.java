package base;

public class NewUser {

    private final int id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;
    private final int userStatus;

    private NewUser(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.phone = builder.phone;
        this.userStatus = builder.userStatus;
    }

    public static class Builder {
        private int id;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phone;
        private int userStatus;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder userStatus(int userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public NewUser build() {
            return new NewUser(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public int getUserStatus() {
        return userStatus;
    }
}
