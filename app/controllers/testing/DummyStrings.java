package controllers.testing;

import java.util.Random;

public class DummyStrings {

    public String getName(){
        String alps="BDFGHJKLMNPRSTVWYZ";
        String vowels="AEIOU";
        String[] alphas=alps.split("");
        String[] vows=vowels.split("");

        String name="";
        Random random=new Random();
        name+=alphas[random.nextInt(alphas.length)]+vows[random.nextInt(vows.length)];
        name=name+alphas[random.nextInt(alphas.length)]+vows[random.nextInt(vows.length)];
        name=name+alphas[random.nextInt(alphas.length)]+vows[random.nextInt(vows.length)];
        return name;

    }
    public String getPhone(){

        StringBuilder phone=new StringBuilder("07");
        String nums="0123456789";
        Random random=new Random();
        for(int i=0;i<8;i++){
            phone.append(nums.split("")[random.nextInt(nums.length())]);
        }
        return phone.toString();
    }

    public Integer year(){
        Random random=new Random();
        return random.nextInt(5);
    }

    public String getEmail(String[] args){
        Random random=new Random();
        String domains="@gmail.com,@yahoo.com,@outlook.com,@microsoft.com,@hotmail.com";
        String email="";


        return email;

    }
}
