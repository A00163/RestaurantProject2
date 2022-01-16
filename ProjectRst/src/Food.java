import java.util.ArrayList;

public class Food {
    String nameFood;
    int time;
    public Food(String nameFood, int time){
        this.nameFood=nameFood;
        this.time =time;
    }
    public static int calculator(ArrayList<Food> foods , String name_food)
    {
        int time_food = 0;
        for (int j=0;j<foods.size();++j)
        {
            if (foods.get(j).nameFood.equals(name_food))
                time_food=foods.get(j).time;
        }
        return time_food;
    }
}
