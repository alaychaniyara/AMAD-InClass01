package inclass1.group3.group3_inclass01.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Users implements Serializable {
  public User user;
  public static class User {
        String _id, email, password, name, age, dateofbirth, address, __v;

        //   private String userName,userId,password;
        // private int age;
        //private double weight;


      @Override
      public String toString() {
          return "User{" +
                  "_id='" + _id + '\'' +
                  ", email='" + email + '\'' +
                  ", password='" + password + '\'' +
                  ", name='" + name + '\'' +
                  ", age='" + age + '\'' +
                  ", dateofbirth='" + dateofbirth + '\'' +
                  ", address='" + address + '\'' +
                  ", __v='" + __v + '\'' +
                  '}';
      }

      public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getDateofbirth() {
            return dateofbirth;
        }

        public void setDateofbirth(String dateofbirth) {
            this.dateofbirth = dateofbirth;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String get__v() {
            return __v;
        }

        public void set__v(String __v) {
            this.__v = __v;
        }
    }
}
