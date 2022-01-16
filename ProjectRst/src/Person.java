public class Person {
    public Person(String name, String name_food, int time_food , int time_eat) {
        this.name = name;
        this.name_food = name_food;
        this.time_eat = time_eat;
        this.time_food= time_food;
        this.sumTime=time_eat+time_food;
    }
    public void setNameOfDesk(char nameOfDesk) {
        this.nameOfDesk = nameOfDesk;
    }
    String name;
    String name_food;
    char nameOfDesk;
    int  time_eat;
    int time_food;
    int sumTime;
}
