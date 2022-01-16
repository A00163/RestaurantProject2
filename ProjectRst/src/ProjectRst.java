import java.util.*;

public class ProjectRst {
    public static Integer getKeyByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to book a restaurant for the party?     1)Yes          2)No");
        int answer=sc.nextInt();
        if(answer==1){
            ArrayList<String>Instructions=new ArrayList<>();
            Map<Integer,String>persons=new HashMap<>();
            AVLTree tree=new AVLTree();
            System.out.println("please enter input:");
            while (sc.hasNextLine()){
                String str=sc.nextLine();
                Instructions.add(str);
            }
            for (String str:Instructions){
                if(str.contains("Insert")){
                    String[]string=str.split(" ");
                    tree.root= tree.insert(tree.root, Integer.parseInt(string[2]));
                    persons.put(Integer.valueOf(string[2]),string[1]);
                }else if(str.contains("Delete")){
                    String[]string=str.split(" ");
                    tree.root= tree.delete(tree.root,getKeyByValue(persons,string[1]));
                }else if(str.contains("ShowTree")){
                    System.out.println("preorder:");
                    tree.preOrder(tree.root);
                    // tree.print2D(tree.root);
                    System.out.println();
                }else if(str.contains("Search")){
                    String[] string=str.split(" ");
                    int number=tree.search(Integer.parseInt(string[1]));
                    if(number!=0){
                        System.out.println(persons.get(number));
                    }

                }
            }
        }else if(answer==2){
            ArrayList<Food> foods = new ArrayList<>();
            Function.definition(foods);
            System.out.println("Please enter the dimensions of the map: ");//وارد کردن ابعاد نقشه
            System.out.println("width: ");//عرض/تعداد سطر ها
            int satr = sc.nextInt();
            System.out.println("length: ");//طول/تعداد ستون ها
            int stoon = sc.nextInt();
            char[][] mapRest = new char[satr][stoon];
            String input;
            char[] convert;
            System.out.println("Please enter the aerial map of the restaurant: ");
            for (int i = 0; i < satr; ++i) {
                input = sc.next();
                convert = input.toCharArray();
                for (int p = 0; p < convert.length; ++p) {
                    mapRest[i][p] = convert[p];
                }
            }
            //-----------------input map
            ArrayList<Character> emptyTables = new ArrayList<>();
            Function.findTables(satr, stoon, mapRest, emptyTables);
            //---------all empty tables
            Function.sortArr(emptyTables);
            if (emptyTables.isEmpty())
            {
                System.out.println("The restaurant is being renovated (we do not have a table).");
                System.exit(0);
            }
            //---------sort empty tables
            System.out.println("\n\u001B[35m" + "***Welcome:)***" + "\u001B[30m\n");
            System.out.println("Please enter the number of people who want to be accepted: ");
            int numberPeople = sc.nextInt();
            ArrayList<Person> persons = new ArrayList<>();
            Map<Person, Character> person_table = new HashMap<>();
            Queue<Person> entezr = new LinkedList<>();
            System.out.println("--------------------Menio--------------------");
            System.out.println(" Chicken\n Kebab\n Rice\n GhormeSabzi\n Gheyme\n Pizza\n lasagna\n Pasta\n Sushi");
            System.out.println("---------------------------------------------");
            System.out.println("Please enter the person information: in the form name,name of food from meno,Duration of eating\n(For example ->  eli Chicken 20)");
            for (int i = 0; i < numberPeople; ++i) {
                String name = sc.next();
                String name_food = sc.next();
                int time_eat = sc.nextInt();
                Person person = new Person(name, name_food, Food.calculator(foods, name_food), time_eat);
                Function.setTable(emptyTables, person, entezr , persons);//set table for any person
            }
            PriorityQueue<Person> printRoute = new PriorityQueue<Person>(Comparator.comparing(person -> person.time_food));
            PriorityQueue<Person> exitPersons = new PriorityQueue<Person>(Comparator.comparing(person -> person.sumTime));
            printRoute.addAll(persons);
            // exitPersons.addAll(persons);
            int size = printRoute.size();
            ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
            int[][] vertices = new int[satr][stoon];
            int num=satr*stoon;
            for (int i = 0; i < num; i++)
                adj.add(new ArrayList<Integer>());
            int count = 0;
            for (int k = 0; k < satr; k++) {
                for (int r = 0; r < stoon; r++) {
                    vertices[k][r] = count;
                    count++;
                }
            }
            Map<Character, Integer> map = new HashMap<>();
            for (int s = 0; s < satr; s++) {
                for (int p = 0; p < stoon; p++) {
                    if (mapRest[s][p] == '$') {
                        map.put('$', vertices[s][p]);
                    }
                    if ((mapRest[s][p] >= 'a' && mapRest[s][p] <= 'z')||(mapRest[s][p] >= 'A' && mapRest[s][p] <= 'Z')) {
                        map.put(mapRest[s][p], vertices[s][p]);
                    }
                }
            }
            ArrayList<ArrayList<Integer>> adj2 = convert(adj, mapRest, vertices, satr, stoon);
            while (!printRoute.isEmpty()){
                Person p = printRoute.poll();
                //System.out.println(printRoute.size());
                ArrayList<Character> allTable=new ArrayList<>();
                Function.findTables(satr,stoon,mapRest,allTable);
                System.out.println("Shortest route from $ to the table " + p.nameOfDesk + " (" + p.name + ")" + " is:");
                printShortestDistance(adj2, map.get('$'), map.get(p.nameOfDesk), num);
                System.out.println();
                //System.out.println(entezr.size());
                exitPersons.add(p);
                Function.PrintExitPersons(exitPersons,emptyTables,entezr,persons,printRoute);
                //System.out.println(printRoute.size());
                // System.out.println(entezr.size());
                System.out.println();
            }
            System.out.println();
        }
    }
    public static ArrayList<ArrayList<Integer>> convert(ArrayList<ArrayList<Integer>> adj, char[][] arr, int[][] vertices, int m, int n) {
        int i = 0, j = 0;
        while (i < m) {
            while (j < n) {
                l:
                if (arr[i][j] == '#') {
                    break l;
                } else if (arr[i][j] == '+') {
                    if (j > 0) {
                        int E = j - 1;
                        if (arr[i][E] == '+' || (arr[i][E] >= 'a' && arr[i][E] <= 'z') || arr[i][E] == '$' || (arr[i][E] >= 'A' && arr[i][E] <= 'Z')) {
                            addEdge(adj, vertices[i][E], vertices[i][j]);
                        }
                    }
                    if (j < n - 1) {
                        int W = j + 1;
                        if (arr[i][W] == '+' || (arr[i][W] >= 'a' && arr[i][W] <= 'z') || arr[i][W] == '$' || (arr[i][W] >= 'A' && arr[i][W] <= 'Z')) {
                            addEdge(adj, vertices[i][W], vertices[i][j]);
                        }
                    }
                    if (i > 0) {
                        int N = i - 1;
                        if (arr[N][j] == '+' || (arr[N][j] >= 'a' && arr[N][j] <= 'z') || arr[N][j] == '$'|| (arr[N][j] >= 'A' && arr[N][j] <= 'Z') ){
                            addEdge(adj, vertices[N][j], vertices[i][j]);
                        }
                    }
                    if (i < m - 1) {
                        int S = i + 1;
                        if (arr[S][j] == '+' || (arr[S][j] >= 'a' && arr[S][j] <= 'z') || arr[S][j] == '$'  || (arr[S][j] >= 'A' && arr[S][j] <= 'Z')) {
                            addEdge(adj, vertices[S][j], vertices[i][j]);
                        }
                    }
                    if (i > 0 && j > 0) {
                        int E = j - 1;
                        int N = i - 1;
                        if (arr[N][E] == '+' || (arr[N][E] >= 'a' && arr[N][E] <= 'z') || arr[N][E] == '$' || (arr[N][E] >= 'A' && arr[N][E] <= 'Z')) {
                            addEdge(adj, vertices[N][E], vertices[i][j]);
                        }
                    }
                    if (i > 0 && j < n - 1) {
                        int W = j + 1;
                        int N = i - 1;
                        if (arr[N][W] == '+' || (arr[N][W] >= 'a' && arr[N][W] <= 'z') || arr[N][W] == '$' || (arr[N][W] >= 'A' && arr[N][W] <= 'Z')) {
                            addEdge(adj, vertices[N][W], vertices[i][j]);
                        }
                    }
                    if (i < m - 1 && j > 0) {
                        int E = j - 1;
                        int S = i + 1;
                        if (arr[S][E] == '+' || (arr[S][E] >= 'a' && arr[S][E] <= 'z') || arr[S][E] == '$' || (arr[S][E] >= 'A' && arr[S][E] <= 'Z')) {
                            addEdge(adj, vertices[S][E], vertices[i][j]);
                        }
                    }
                    if (i < m - 1 && j < n - 1) {
                        int W = j + 1;
                        int S = i + 1;
                        if (arr[S][W] == '+' || (arr[S][W] >= 'a' && arr[S][W] <= 'z') || arr[S][W] == '$' || (arr[S][W] >= 'A' && arr[S][W] <= 'Z')) {
                            addEdge(adj, vertices[S][W], vertices[i][j]);
                        }
                    }
                } else if (arr[i][j] == '$') {
                    if (j > 0) {
                        int E = j - 1;
                        if (arr[i][E] == '+' || (arr[i][E] >= 'a' && arr[i][E] <= 'z') || (arr[i][E] >= 'A' && arr[i][E] <= 'Z')) {
                            addEdge(adj, vertices[i][E], vertices[i][j]);
                        }
                    }
                    if (j < n - 1) {
                        int W = j + 1;
                        if (arr[i][W] == '+' || (arr[i][W] >= 'a' && arr[i][W] <= 'z')|| (arr[i][W] >= 'A' && arr[i][W] <= 'Z')) {
                            addEdge(adj, vertices[i][W], vertices[i][j]);
                        }
                    }
                    if (i > 0) {
                        int N = i - 1;
                        if (arr[N][j] == '+' || (arr[N][j] >= 'a' && arr[N][j] <= 'z')|| (arr[N][j] >= 'A' && arr[N][j] <= 'Z')) {
                            addEdge(adj, vertices[N][j], vertices[i][j]);
                        }
                    }
                    if (i < m - 1) {
                        int S = i + 1;
                        if (arr[S][j] == '+' || (arr[S][j] >= 'a' && arr[S][j] <= 'z') || (arr[S][j] >= 'A' && arr[S][j] <= 'Z')) {
                            addEdge(adj, vertices[S][j], vertices[i][j]);
                        }
                    }
                    if (i > 0 && j > 0) {
                        int E = j - 1;
                        int N = i - 1;
                        if (arr[N][E] == '+' || (arr[N][E] >= 'a' && arr[N][E] <= 'z')|| (arr[N][E] >= 'A' && arr[N][E] <= 'Z')) {
                            addEdge(adj, vertices[N][E], vertices[i][j]);
                        }
                    }
                    if (i > 0 && j < n - 1) {
                        int W = j - 1;
                        int N = i - 1;
                        if (arr[N][W] == '+' || (arr[N][W] >= 'a' && arr[N][W] <= 'z')|| (arr[N][W] >= 'A' && arr[N][W] <= 'Z')) {
                            addEdge(adj, vertices[N][W], vertices[i][j]);
                        }
                    }
                    if (i < m - 1 && j > 0) {
                        int E = j - 1;
                        int S = i + 1;
                        if (arr[S][E] == '+' || (arr[S][E] >= 'a' && arr[S][E] <= 'z')|| (arr[S][E] >= 'A' && arr[S][E] <= 'Z')) {
                            addEdge(adj, vertices[S][E], vertices[i][j]);
                        }
                    }
                    if (i < m - 1 && j < n - 1) {
                        int W = j + 1;
                        int S = i + 1;
                        if (arr[S][W] == '+' || (arr[S][W] >= 'a' && arr[S][W] <= 'z')|| (arr[S][W] >= 'A' && arr[S][W] <= 'Z')) {
                            addEdge(adj, vertices[S][W], vertices[i][j]);
                        }
                    }
                } else if ((arr[i][j] >= 'a' && arr[i][j] <= 'z') || (arr[i][j] >= 'A' && arr[i][j] <= 'Z')) {
                    if (j > 0) {
                        int E = j - 1;
                        if (arr[i][E] == '+' || (arr[i][E] >= 'a' && arr[i][E] <= 'z') || arr[i][E] == '$'|| (arr[i][E] >= 'A' && arr[i][E] <= 'Z')) {
                            addEdge(adj, vertices[i][E], vertices[i][j]);
                        }
                    }
                    if (j < n - 1) {
                        int W = j + 1;
                        if (arr[i][W] == '+' || (arr[i][W] >= 'a' && arr[i][W] <= 'z') || arr[i][W] == '$'|| (arr[i][W] >= 'A' && arr[i][W] <= 'Z')) {
                            addEdge(adj, vertices[i][W], vertices[i][j]);
                        }
                    }
                    if (i > 0) {
                        int N = i - 1;
                        if (arr[N][j] == '+' || (arr[N][j] >= 'a' && arr[N][j] <= 'z') || arr[N][j] == '$'|| (arr[N][j] >= 'A' && arr[N][j] <= 'Z')) {
                            addEdge(adj, vertices[N][j], vertices[i][j]);
                        }
                    }
                    if (i < m - 1) {
                        int S = i + 1;
                        if (arr[S][j] == '+' || (arr[S][j] >= 'a' && arr[S][j] <= 'z') || arr[S][j] == '$'|| (arr[S][j] >= 'A' && arr[S][j] <= 'Z')) {
                            addEdge(adj, vertices[S][j], vertices[i][j]);
                        }
                    }
                    if (i > 0 && j > 0) {
                        int E = j - 1;
                        int N = i - 1;
                        if (arr[N][E] == '+' || (arr[N][E] >= 'a' && arr[N][E] <= 'z') || arr[N][E] == '$'|| (arr[N][E] >= 'A' && arr[N][E] <= 'Z')) {
                            addEdge(adj, vertices[N][E], vertices[i][j]);
                        }
                    }
                    if (i > 0 && j < n - 1) {
                        int W = j + 1;
                        int N = i - 1;
                        if (arr[N][W] == '+' || (arr[N][W] >= 'a' && arr[N][W] <= 'z') || arr[N][W] == '$'|| (arr[N][W] >= 'A' && arr[N][W] <= 'Z')) {
                            addEdge(adj, vertices[N][W], vertices[i][j]);
                        }
                    }
                    if (i < m - 1 && j > 0) {
                        int E = j - 1;
                        int S = i + 1;
                        if (arr[S][E] == '+' || (arr[S][E] >= 'a' && arr[S][E] <= 'z') || arr[S][E] == '$'|| (arr[S][E] >= 'A' && arr[S][E] <= 'Z')) {
                            addEdge(adj, vertices[S][E], vertices[i][j]);
                        }
                    }
                    if (i < m - 1 && j < n - 1) {
                        int W = j + 1;
                        int S = i + 1;
                        if (arr[S][W] == '+' || (arr[S][W] >= 'a' && arr[S][W] <= 'z') || arr[S][W] == '$'|| (arr[S][W] >= 'A' && arr[S][W] <= 'Z')) {
                            addEdge(adj, vertices[S][W], vertices[i][j]);
                        }
                    }
                }
                j++;
            }
            j = 0;
            i++;
        }
        return adj;
    }

    static void addEdge(ArrayList<ArrayList<Integer>> adj, int u, int v) {
        if (!adj.get(u).contains(v)) {
            adj.get(u).add(v);
        }
        if (!adj.get(v).contains(u)) {
            adj.get(v).add(u);
        }
    }

    private static void printShortestDistance(ArrayList<ArrayList<Integer>> adj, int s, int dest, int v) {
        int pred[] = new int[v];
        if (BFS(adj, s, dest, v, pred) == false) {
            System.out.println("CHE KHAKI BE SARAM KONAM GHAZAM YAKH KARD");
            return;
        }
        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
    }

    private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src, int dest, int v, int pred[]) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[v];
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            pred[i] = -1;
        }
        visited[src] = true;
        queue.add(src);
       l2: while (!queue.isEmpty()) {
            int u = queue.remove();
            if (adj.get(u).isEmpty())
            {
                break l2;
            }
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }
}
