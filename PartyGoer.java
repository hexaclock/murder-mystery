public class PartyGoer
{
    private String name;
    //0 to 10 intelligence level
    private int wits;
    //-10 (investigators), 0 to 10 (everyone else) homicidal level
    private int HL;
    //0 to 10 creepiness level
    private int CL;
    //Just a string for name of tool
    private String tool;
    //0 to 10 tool danger level; how useful the tool is
    private int TDL;
    //committed a murder
    private boolean didIt;
    //been busted
    private boolean busted;
    //partygoer alive or not
    private boolean alive;
    //location that partygoer is currently in
    private Room location;
    //number of times partygoer has been interrogated
    private int numinters;
    
    /**
     *@param n String Name
     *@param wit int Intelligence Level
     *@param h int Homicidal Level
     *@param c int Creepiness Level
     *@param t String Tool
     *@param tdl int Tool Damage Level
     */
    public PartyGoer(String n, int wit, int h, int c, String t, int tdl)
        {
            name = n;
            wits = wit;
            HL = h;
            CL = c;
            tool = t;
            TDL = tdl;
            didIt = false;
            busted = false;
            location = null;
            alive = true;
        }
    public void bustPartyGoer()
        {
            this.busted = true;
            this.alive = false;
            PartyGoer[] currentpgsinroom = this.getLocation().getPartyGoersInRoom();
            PartyGoer[] newpgsinroom = new PartyGoer[currentpgsinroom.length];
            for (int i=0;i<currentpgsinroom.length;i++)
            {
                if (currentpgsinroom[i] == null)
                    System.out.print("");
                else if (currentpgsinroom[i].equals(this))
                    System.out.print("");
                else
                {
                    newpgsinroom[i] = currentpgsinroom[i];
                }
            }
            this.getLocation().setPartyGoersInRoom(newpgsinroom);

        }
    /**
     *@return boolean True/False depending on whether party goer has been busted or not.
     */
    public boolean isBusted()
        {
            return this.busted;
        }
    public void killPartyGoer()
        {
            this.alive = false;
            PartyGoer[] currentpgsinroom = this.getLocation().getPartyGoersInRoom();
            PartyGoer[] newpgsinroom = new PartyGoer[currentpgsinroom.length];
            for (int i=0;i<currentpgsinroom.length;i++)
            {
                if (currentpgsinroom[i] == null)
                    System.out.print("");
                else if (currentpgsinroom[i].equals(this))
                    System.out.print("");
                else
                {
                    newpgsinroom[i] = currentpgsinroom[i];
                }
            }
            this.getLocation().setPartyGoersInRoom(newpgsinroom);
        }
    /**
     *@return int Number of times a party goer has been interrogated
     */
    public int getNumInterrogations()
        {
            return this.numinters;
        }
    /**
     *@return int The number of interrogations
     */
    public int incrementNumInterrogations()
        {
            this.numinters++;
            return this.numinters;
        }
    public void setGuilty()
        {
            this.didIt = true;
        }
    /**
     *@return boolean True/False depending on whether or not party goer has killed someone
     */
    public boolean isGuilty()
        {
            return this.didIt;
        }
    /**
     *@return String Tool
     */
    public String getTool()
        {
            return this.tool;
        }
    /**
     *@return boolean True/False depending on whether or not party goer is alive
     */
    public boolean isAlive()
        {
            return this.alive;
        }
    /**
     *@return String Name of party goer
     */
    public String getName()
        {
            return this.name;
        }
    /**
     *@return int Tool danger level
     */
    public int getTDL()
        {
            return this.TDL;
        }
    /**
     *@return int Homicidal level
     */
    public int getHL()
        {
            return this.HL;
        }
    /**
     *@return int Creepiness level
     */
    public int getCL()
        {
            return this.CL;
        }
    /**
     *@return int Wit level
     */
    public int getWits()
        {
            return this.wits;
        }
    /**
     *@return Room Party Goer location
     */
    public Room getLocation()
        {
            return this.location;
        }
    public void setLocation(Room r)
        {
            this.location = r;
        }
    /**
     *@return String Textual representation of the party goer
     */
    public String toString()
        {
            return "Name: "+name+"\n"+"Wits: "+wits+"\n"+"Homicidal Level: "+HL+"\n"+"Creepiness Level: "+CL+"\n"+"Tool: "+tool+"\n"+"Tool Damage Level: "+TDL+"\n";
        }
}
