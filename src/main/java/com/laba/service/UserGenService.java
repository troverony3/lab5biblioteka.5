package com.laba.service;
import com.laba.model.Student;
import com.laba.model.Teacher;
import com.laba.model.User;
import com.laba.model.enums.Sex;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UserGenService {

    public static List<User> createUsers(Class<? extends User> userClass, List<String> firstNamesMale, List<String> firstNamesFemale, List<String> surnames) {
        List<User> users = new ArrayList<>();
        firstNamesMale.forEach(name -> users.add(createUser(name, Sex.MALE, userClass)));
        firstNamesFemale.forEach(name -> users.add(createUser(name, Sex.FEMALE, userClass)));
        users.forEach(user -> {
            String maleSurname = surnames.get(ThreadLocalRandom.current().nextInt(surnames.size()));
            if (user.getSex().equals(Sex.MALE)) {
                user.setLastName(maleSurname);
            } else {
                user.setLastName(modifySurnameToFemale(maleSurname));
            }
        });
        return users;
    }

    private static String modifySurnameToFemale(String maleSurname) {
        if (maleSurname.endsWith("ов") || maleSurname.endsWith("ин") || maleSurname.endsWith("ев") || maleSurname.endsWith("ёв")) {
            return maleSurname + "а";
        } else if (maleSurname.endsWith("кий")) {
            return maleSurname.substring(0, maleSurname.length() - 3) + "кая";
        } else {
            return maleSurname;
        }
    }

    private static User createUser(String name, Sex sex, Class<? extends User> userClass) {
        if (userClass.equals(Student.class)) {
            return new Student().setFirstName(name).setSex(sex);
        } else {
            return new Teacher().setFirstName(name).setSex(sex);
        }
    }

    public static void addMiddleNames(List<User> users, List<String> middlenames) {
        users.forEach(user -> {
            String maleMiddlename = middlenames.get(ThreadLocalRandom.current().nextInt(middlenames.size()));
            if (user.getSex().equals(Sex.FEMALE)) {
                maleMiddlename = maleMiddlename.substring(0, maleMiddlename.length() - 2) + "на";
            }
            ((Teacher) user).setMiddleName(maleMiddlename);
        });
    }

    public static User createUserFromGUI(String firstName, String lastName, String middleName, Sex sex, Class<? extends User> userClass) {
        if (userClass.equals(Student.class)) {
            return new Student().setFirstName(firstName).setLastName(lastName).setSex(sex);
        } else {
            return new Teacher().setMiddleName(middleName).setFirstName(firstName).setLastName(lastName).setSex(sex);
        }
    }
}
