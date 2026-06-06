package rvt.Interface_In_A_Box;

public class CD implements Packable{
    private String artist;
    private String name;
    private Integer publicationYear;

   public CD(String artist, String name, Integer publicationYear){
    this.artist = artist;
    this.name = name;
    this.publicationYear = publicationYear;
   }
   
    @Override
    public double weight() {

        return 0.1;
    }
    
    @Override 
    public String toString() {
        return this.artist + ": " + this.name + " (" + this.publicationYear + ")" ;
    }

}
