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
        return random.nextInt(4)+1;
    }

    public String getEmail(String[] args){

        Random random=new Random();
        String domains="@gmail.com,@yahoo.com,@outlook.com,@microsoft.com,@hotmail.com";

        String email="";
        email+=args[random.nextInt(args.length)]+"_"+args[random.nextInt(args.length)]+random.nextInt(100)+domains.split(",")[random.nextInt(domains.split(",").length)];
        return email.toLowerCase();

    }
    public String getRegNo(){
        Random random=new Random();
        String[] years={"015","016","017","018"};

        return "N11/3/"+1+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+"/"+years[random.nextInt(years.length)];
    }
    public String getWorkId(){
        Random random= new Random();

        return "L"+(random.nextInt(500)+100);
    }

    public String getTitle(){
        String[] titles={"Mr","Mrs","Prof","Ms","Dr"};
        Random random=new Random();
        return titles[random.nextInt(titles.length)];
    }
    public  String getUnitCode(){
        Random random=new Random();
        String[] unIs={"1","2","3","4"};
        return "Comp "+unIs[random.nextInt(unIs.length)]+""+random.nextInt(2)+""+random.nextInt(7);
    }
}
