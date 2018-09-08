package inclass1.group3.group3_inclass01;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    /*"status": "ok",
            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MjI3MTUzNTYsImV4cCI6MTU1NDI1MTM1NiwianRpIjoiMjc0TENtQXFSV2kzZ1Z0M1p4bFhoSSIsInVzZXIiOjc0MX0.vBJ7RhRcEoMKLCXbKoymUPkjPQuHxyUaIHsyMXkfAkI",
            "user_id": admin_suer,
            "user_email": "admin_user@gmail.com",
            "user_name": "Admin User",
            "age": "25",
            "weight": "120"*/

        String _id,email,password,name,age,dateofbirth,address,__v;
      //  String status, userId,userName,password,age,weight;


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

    }
