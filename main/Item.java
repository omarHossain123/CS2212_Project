public interface Item{
    private double item_price;
    private int number_item;
    private int item_max;
    private int type;
    private String label;

    private String get_label();
    private int get_type();
    private void decrease_item();
    private double get_price();
    private int get_number_item();
    private int get_item_max();
    private void increase_item(int bought);
}