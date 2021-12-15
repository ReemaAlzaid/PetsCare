package com.example.petscarenew;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import junit.framework.TestCase;

import java.util.List;

public class CreatePetTest extends TestCase {
    public void testValidateFields() {
        System.out.println("Insert valid inputs");
        CreatePet instance = new CreatePet();
        String name="Clifford";
        String age="1";
        String des="My sister's dog. Eats 3 times";
        Boolean expResult=true;
        Boolean result=instance.validateFieldsTest(name,age,des);
        Log.d("myTag", "This is my message");
        assertEquals(expResult,result);
    }
    public void testAge() {
        CreatePet instance = new CreatePet();
        String name="Clifford";
        String age="999";
        String des="Clifford is my sister's dog he is the best";
        Boolean expResult=false;
        Boolean result=instance.validateFieldsTest(name,age,des);
        assertEquals(expResult,result);
    }
    public void testNothinginputs() {
        CreatePet instance = new CreatePet();
        String name="";
        String age="";
        String des="";
        Boolean expResult=false;
        Boolean result=instance.validateFieldsTest(name,age,des);
        assertEquals(expResult,result);
    }
    public void testName() {
        CreatePet instance = new CreatePet();
        String name="Clifford is my sister's dog he is the best";
        String age="2";
        String des="Clifford is my sister's dog he is the best";
        Boolean expResult=false;
        Boolean result=instance.validateFieldsTest(name,age,des);
        assertEquals(expResult,result);
    }
    public void testDescription() {
        CreatePet instance = new CreatePet();
        String name="Clifford";
        String age="1";
        String des="Clifford is my sister's dog he is the best likes treats very much and loves treats especially the one from the supermarket near us";
        Boolean expResult=false;
        Boolean result=instance.validateFieldsTest(name,age,des);
        assertEquals(expResult,result);
    }
    public void testAgeIfString() {
        CreatePet instance = new CreatePet();
        String name="Clifford";
        String age="One year";
        String des="My sister's dog. Eats 3 times";
        Boolean expResult=false;
        Boolean result=instance.validateFieldsTest(name,age,des);
        assertEquals(expResult,result);
    }

}