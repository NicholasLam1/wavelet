import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
 
class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> match = new ArrayList<String>();
 
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num);
        } else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    for (int i=0; i<list.size(); i++){
                        if (list.get(i).indexOf(parameters[1]) != -1) {
                            match.add(list.get(i));
                        }
                    }
                    String s = "Matches: ";
                    for (int i=0; i<match.size(); i++){
                        s = s + " " + String.format(match.get(i));
                    }
                    return s;
                }
               
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    list.add(parameters[1]);
                }
                return String.format("Done: added %s", parameters[1]);
            }
            return "404 Not Found!";
        }return String.format("Done");
    }
   
}
 
class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }
 
        int port = Integer.parseInt(args[0]);
 
        Server.start(port, new Handler());
    }
}
 
