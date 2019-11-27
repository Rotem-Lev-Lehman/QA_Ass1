public class Main {

    public static void main(String[] args) {
        NamesMaster namesMaster=new NamesMaster();
        try{
            if (args.length ==1){
                if(args[0].equals("GenerateName")){

                }
                else {
                    badInput();
                }
            }
            else if (args.length ==2){
                if(args[0].equals("CountSpecificString")){
                    namesMaster.CountSpecificString(args[1]);
                }
                else if(args[0].equals("CountAllStrings")){
                    int length = Integer.parseInt(args[1]);
                    if(length<=0){
                        badInput();
                    }
                    else {
                        namesMaster.CountAllStrings(length);
                    }
                }
                else if(args[0].equals("CountMaxString")){
                    int length = Integer.parseInt(args[1]);
                    if(length<=0){
                        badInput();
                    }
                    else {
                        namesMaster.CountMaxString(length);
                    }
                }
                else if(args[0].equals("AllIncludesString")){
                    namesMaster.AllIncludesString(args[1]);
                }
                else {
                    badInput();
                }
            }
            else {
                badInput();
            }
        }
        catch (Exception e){
            badInput();
        }
        namesMaster.finished();
    }

    private static void badInput(){
        System.out.println("Bad parameters");
    }
}
