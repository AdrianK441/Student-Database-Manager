package assi1.assignment1.Model;

/**
 * This Class creates a Module Object and methods associated with it
 */
public class Modules{
    private final String module_id;
    private final String module_name;
    private final int module_result;

    /**
     * This Constructor creates a new Module object with the inserted parameters
     * @param module_id String used as module_id
     * @param name String used as module name
     * @param result int used as the result for the module
     */
    public Modules(String module_id, String name, int result){
        this.module_id = module_id;
        this.module_name = name;
        this.module_result = result;
    }

    /**
     * This method returns the name of the Module Object
     * @return String module name
     */
    public String getModule_name() {
        return module_name;
    }

    /**
     * This method returns the results of the module object
     * @return int module results
     */
    public int getModule_result() {
        return module_result;
    }

    /**
     * This method returns the id of the module Object
     * @return String module id
     */
    public String getModule_id(){
        return module_id;
    }

    /**
     * This method formats the information of the Module object into a presentable String
     * @return String representing information of the Module object
     */
    public String FormatToString(){
        return  "ID: "+module_id+"\n"+
                "Module Name: "+module_name+"\n"+
                "Grade: "+module_result+"\n";
    }
}
