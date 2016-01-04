public class Room
{
    private String roomname;
    private int roomcap;
    private int numppl;
    private PartyGoer[] pgsinroom;
    private boolean hascorpse;

    /**
     *@param name String for the name
     *@param cap int for the room capacity
     */
    public Room(String name, int cap)
        {
            roomname = name;
            roomcap = cap;
            pgsinroom = new PartyGoer[roomcap];
            numppl = 0;
            hascorpse = false;
        }
    /**
     *@return int Number of party goers in the room
     */
    public int getOccupantAmt()
        {
            return this.numppl;
        }
    /**
     *@return String The name of the room
     */
    public String getRoomName()
        {
            return this.roomname;
        }
    /**
     *@return PartyGoer[] Array of partygoers in the room
     */
    public PartyGoer[] getPartyGoersInRoom()
        {
            return this.pgsinroom;
        }
    /**
     *@return PartyGoer[] Array of only investigators in the room
     */
    public PartyGoer[] getInvestigators()
        {
            PartyGoer[] investigators = new PartyGoer[this.pgsinroom.length];
            for (int i=0;i<this.pgsinroom.length;i++)
            {
                if (this.getPG(i) == null)
                    System.out.print("");
                else if (this.getPG(i).getHL() == -10)
                    investigators[i] = this.pgsinroom[i];
            }
            return investigators;
        }
    /**
     *@param newpgs PartyGoer[] array of partygoers that will overwrite the array of partygoers currently in the room
     */
    public void setPartyGoersInRoom(PartyGoer[] newpgs)
        {
            this.pgsinroom = newpgs;
        }
    /**
     *@param index int The index in the room
     *@return PartyGoer The party goer at the index
     */
    public PartyGoer getPG(int index)
        {
            return this.pgsinroom[index];
        }
    public void decrNumPPL()
        {
            this.numppl--;
        }
    /**
     *@return boolean True/False depending on whether the room contains a corpse or not.
     */
    public boolean containsCorpse()
        {
            return this.hascorpse;
        }
    public void setCorpse()
        {
            this.hascorpse = true;
        }
    /**
     *@return int The number of people in the room
     */
    public int getNumPeople()
        {
            return this.numppl;
        }
    /**
     *@return int The room's maximum capacity
     */
    public int getRoomCap()
        {
            return this.roomcap;
        }
    public void expand()
        {
            this.roomcap++;
            PartyGoer[] tmp = new PartyGoer[this.roomcap];
            for (int i=0;i<pgsinroom.length;i++)
            {
                tmp[i] = pgsinroom[i];
            }
            pgsinroom = tmp;
        }       
    /**
     *@param pg PartyGoer to add to the room
     *@return True/False depending on whether add operation succeeded or not
     */
    public boolean addPartyGoerToRoom(PartyGoer pg)
        {
            if (numppl<roomcap)
            {
                for (int i=0;i<roomcap;i++)
                {
                    if (pgsinroom[i] == null)
                    {
                        pgsinroom[i] = pg;
                        numppl++;
                        break;
                    }
                }
                return true;
            }
            return false;
        }
    /**
     *@param pg PartyGoer to remove from the room
     *@return boolean True/False depending on whether operation succeeded or not
     */
    public boolean removePartyGoerFromRoom(PartyGoer pg)
        {
            for (int i=0;i<this.roomcap;i++)
            {
                if (this.pgsinroom[i]==null)
                    System.out.print("");
                else
                {
                    if ((this.pgsinroom[i]).equals(pg))
                    {
                        pgsinroom[i] = null;
                        pg.setLocation(null);
                        this.numppl--;
                        return true;
                    }
                }       
            }
            return false;
        }
    /**
     *@return String Textual representation of the room
     */
    public String toString()
        {
            return "Room: "+roomname+"\n"+"RoomCap: "+roomcap+"\n"+"OccupantAmt: "+numppl+"\n";
        }
}
