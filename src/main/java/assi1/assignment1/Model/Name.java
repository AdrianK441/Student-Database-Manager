package assi1.assignment1.Model;

/**
 * This class creates a Name Object and handles methods related to it
 */
public class Name {
    private String FirstName = "";
    private String MiddleName = "";
    private String LastName = "";

    /**
     * This contructor takes in a String of multiple words, splits the string into an array and
     * distributes the words between Firstname, Middlename and Lastname
     *
     * @param name String name
     */
    public Name(String name){
        String[] splitText = name.split(" ");
        int i;
        StringBuilder middle_name = new StringBuilder();

        for(i = 0; i < splitText.length; i++){
            if(i == 0){this.FirstName = splitText[i];}
            else if( i == splitText.length - 1){this.LastName = splitText[i];
            }else{
                middle_name.append(splitText[i]);
            }
        }
        this.MiddleName = middle_name.toString();
    }

    /**
     * This method takes Firstname, Middlename and lastname and appends them to a single string and returns it
     *
     * @return String made up of First, middle and last names of the Object
     */
    public String getFullName(){
        StringBuilder FullName = new StringBuilder();
        FullName.append(FullName).append(FirstName).append(" ").append(MiddleName).append(" ").append(LastName);
        return FullName.toString();
    }
}
