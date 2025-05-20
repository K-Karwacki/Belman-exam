package dk.easv.belmanexam;

import dk.easv.belmanexam.utils.PasswordHasher;

public class hashprint {

    public static void main(String[] args) {
        System.out.println(PasswordHasher.hashPassword("admin"));
    }
}
