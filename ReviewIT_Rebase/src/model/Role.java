package model;

public enum Role {
    AUTHOR("Author"),
    SUBPROGRAM("Subprogram Chair"),
    PROGRAM_CHAIR("Program Chair"),
    REVIEWER("Reviewer");
	
	private final String myRoleName;
	
	Role(final String theRoleName){
		myRoleName = theRoleName;
	}
	
	public String getRoleName(){
		return myRoleName;
	}
}
