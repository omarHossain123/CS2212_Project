package main;
import Item;

public class Toy implements Item {
    private double item_price;
    private int number_item;
    private int item_max;
    private int type;
    private String label;
    private double happiness_bonus;

    public Toy(int type, int number_item){
        switch(type){
            case 1: 
                this.item_price = 40;
                this.number_item = number_item;
                this.item_max = 50;
                this.label = "Chew Toy";
                this.happiness_bonus = 35;
            break;
            case 2: 
                this.item_price = 25;
                this.number_item = number_item;
                this.item_max = 10;
                this.label = "Ball";
                this.happiness_bonus = 20;
            break;
            default:
                this.item_price = 0;
                this.number_item = 0;
                this.item_max = 0;
                this.label = "";
                this.happiness_bonus = 0;
        }
    }

    private double get_happiness_bonus(){
        return happiness_bonus;
    }

    @Override
    private String get_label(){
        return label;
    }
    @Override
    private int get_type(){
        return type;
    }
    @Override 
    private void decrease_item(){
        if (number_item > 0){
            this.number_item--;
        }
    }
    @Override 
    private double get_price(){
        return item_price;
    }
    @Override 
    private int get_number_item(){
        return number_item;
    }
    @Override 
    private int get_item_max(){
        return item_max;
    }
    @Override 
    private void increase_item(int bought){
        if ((number_item + bought) <= item_max){
            this.number_item += bought;
        }
    }
    
}