package main;
class Food implements Item {
    private double item_price;
    private int number_item;
    private int item_max;
    private int type;
    private String label;
    private double hunger_bonus;

    public Food(int type, int number_item){
        switch(type){
            case 1: 
                this.item_price = 10;
                this.number_item = number_item;
                this.item_max = 100;
                this.label = "Apple";
                this.hunger_bonus = 10;
            break;
            case 2: 
                this.item_price = 15;
                this.number_item = number_item;
                this.item_max = 100;
                this.label = "Banana";
                this.hunger_bonus = 12;
            break;
            default:
                this.item_price = 0;
                this.number_item = 0;
                this.item_max = 0;
                this.label = "";
                this.hunger_bonus = 0;
        }
    }

    private double get_hunger_bonus(){
        return hunger_bonus;
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