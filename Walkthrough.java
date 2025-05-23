import java.sql.*;
import java.util.*;
import java.lang.String;

public class Walkthrough
{
    private static final Map<String, Integer> columnNums = new HashMap<>();
    private static final Map<String, String> columns = new HashMap<>();
    private static String columnName[];
    private static String columnType[];
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        //Trans trans = new Trans();

        while (true) 
        {
            System.out.println("Enter command (create, insert, query, exit):");
            String command = scanner.nextLine();

            switch (command) 
            {
                case "create":
                    // Prompt for table name, columns, etc.
                    // Call trans.create()
                    System.out.println("Enter table name:");
                    String tableName = scanner.nextLine();
                    if (Trans.exist(tableName)==true)
                    {
                        System.out.println("Table with the name "+tableName+" already exists. Choose a different table name.");
                        break;
                    }
                    System.out.println("How many columns?");
                    int columnNum = Integer.parseInt(scanner.nextLine());
                    columnName = new String[columnNum];
                    columnType = new String[columnNum];
                    columnNums.put(tableName, columnNum);
                    for (int i=0; i<columnNum; i++)
                    {
                        System.out.println("Enter column "+i+" name:");//and variable types (example: 'id VARCHAR(6), quiz INT, avg decimal(3,2))'");
                        columnName[i]=scanner.nextLine();
                        System.out.println("Enter column "+i+" variable type (VARCHAR(6), INT, float, or decimal(3,2)):");
                        columnType[i]=scanner.nextLine();
                        columns.put(columnName[i],columnType[i]);
                    }
                    String values="";
                    for (int i=0; i<columnNum-1; i++)
                    {
                        if (i!=columnNum-1)
                        {
                            values+=columnName[i]+" "+columnType[i]+",";
                        }
                        else
                        {
                            values+=columnName[i]+" "+columnType[i];
                        }
                    }
                    //System.out.println(values);
                    //System.out.println("Enter column names and variable types (example: 'id VARCHAR(6), quiz INT, avg decimal(3,2))'");
                    //String values = scanner.nextLine();
                    Trans.create(tableName, values);
                    break;
                case "insert":
                    // Prompt for values to insert
                    String[] vals;
                    System.out.println("Enter table name:");
                    String tableName1 = scanner.nextLine();
                    if (Trans.exist(tableName1)==false)
                    {
                        System.out.println("Table with the name "+tableName1+" does not exist. Choose an existing table.");
                        break;
                    }
                    int numColumns = columnNums.getOrDefault(tableName1, 0);
                    if (numColumns==0)
                    {
                        System.out.println("Column count is 0. Cannot enter values.");
                        break;
                    }
                    else
                    {
                        vals = new String[numColumns];
                        for (int i=0; i<numColumns; i++)
                        {
                            System.out.println("Enter this type of value: "+columnType[i]+" for this column: "+columnName[i]);
                            vals[i]=scanner.nextLine();
                        }
                    }
                    Trans.write(tableName1, vals);
                    // Call trans.write()
                    break;
                case "query":
                    System.out.println("Enter table name:");
                    String tableName2=scanner.nextLine();
                    if (Trans.exist(tableName2)==false)
                    {
                        System.out.println("Table with the name "+tableName2+" does not exist. Choose an existing table.");
                        break;
                    }
                    int numColumns2 = columnNums.getOrDefault(tableName2, 0);
                    if (numColumns2==0)
                    {
                        System.out.println("No columns exist in this table. Cannot do a query.");
                        break;
                    }
                    else
                    {
                        System.out.println("Enter column name you would like to do a query on (or put '*' for all columns):");
                        String columnName2=scanner.nextLine();
                        if (Trans.found(tableName2,columnName2,"")==false && !columnName2.contains("*"))
                        {
                            System.out.println("column "+columnName2+" does not exist in table "+tableName2+". Try again.");
                            break;
                        }
                        else
                        {
                            System.out.println("enter WHERE clause (or leave blank for all rows):");
                            String condition = scanner.nextLine();
                            System.out.println(Trans.read(tableName2, columnName2, condition));
                        }
                    }
                    // Prompt for column and WHERE clause
                    // Call trans.read() and print results
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid command.");
            }
        }
    }
}