import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Function {
    static void findTables(int satr, int stoon, char[][] map, ArrayList<Character> allTables) {
        for (int i = 0; i < satr; ++i) {
            for (int j = 0; j < stoon; ++j) {
                if ((map[i][j] >= 'a' && map[i][j] <= 'z') || (map[i][j] >= 'A' && map[i][j] <= 'Z'))
                    allTables.add(map[i][j]);
            }
        }
    }

    static void setTable(ArrayList<Character> emptyTables, Person person, Queue<Person> entzar , ArrayList<Person> persons) {
        if (!emptyTables.isEmpty()) {
            char table = emptyTables.get(0);
            System.out.println("\u001B[34m" +person.name+ ",Your table is table " + table + ".Please here you are:)" + "\u001B[30m\n");
            emptyTables.remove(0);
            person.nameOfDesk=table;
            persons.add(person);
        } else {
            System.out.println("\u001B[34m" + "Sorry dear " + person.name + ",the restaurant capacity is full " + "\u001B[30m\n");
            entzar.add(person);
        }
    }

    static void sortArr(ArrayList<Character> array) {
        char temp;
        for (int i = 1; i < array.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (array.get(j) < array.get(j - 1)) {
                    temp = array.get(j);
                    array.set(j, array.get(j - 1));
                    array.set(j - 1, temp);
                }
            }
        }
    }

    static void definition(ArrayList<Food> foods)
    {
        Food f1=new Food("Chicken",20);
        Food f2=new Food("Kebab",22);
        Food f3=new Food("Rice",10);
        Food f4=new Food("GhormeSabzi",25);
        Food f5=new Food("Gheyme",23);
        Food f6=new Food("Pizza",16);
        Food f7=new Food("lasagna",21);
        Food f8=new Food("Pasta",24);
        Food f9=new Food("Sushi",12);
        foods.add(f1);
        foods.add(f2);
        foods.add(f3);
        foods.add(f4);
        foods.add(f5);
        foods.add(f6);
        foods.add(f7);
        foods.add(f8);
        foods.add(f9);
    }
    static void PrintExitPersons(PriorityQueue<Person> personPr , ArrayList<Character> emptyTables,Queue<Person> entzar,ArrayList<Person> persons,PriorityQueue<Person> printRoute  )
    {
        Person current;
        int size=personPr.size();
        for (int i=0;i<size;++i)
        {
            current=personPr.poll();
            System.out.println(current.name + " finished eating and left the restaurant. Goodbye " +current.name + ":)");
            emptyTables.add(current.nameOfDesk);
            if(!entzar.isEmpty()){
                Person p=entzar.poll();
                setTable(emptyTables,p,entzar,persons);
                printRoute.add(p);
            }
        }
    }
}
