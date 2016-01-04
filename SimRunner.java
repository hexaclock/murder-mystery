import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class SimRunner
{
    private Room[] roomarr = parseRooms();
    private PartyGoer[] pgarr = parsePartyGoers();
    //create special garden room not in the roomarr
    private Room garden = new Room("Garden",pgarr.length);
    private static SimRunner sim  = new SimRunner();

    /**
     *The main function
     */
    public static void main(String[] args)
        {
            //SimRunner sim = new SimRunner();
            sim.menu();
        }
    private void menu()
        {
            int choice = 0;
            Scanner keyboard = new Scanner(System.in);
            while(true)
            {
                System.out.println("1) Add Party Goer");
                System.out.println("2) Add Room");
                System.out.println("3) View Party Goers");
                System.out.println("4) View Rooms");
                System.out.println("5) Start Simulation");
                System.out.println("0) Exit");
                try {choice = keyboard.nextInt();}
		catch (InputMismatchException e) {System.out.println("Please select a valid option."); menu();}
		if (choice==0)
                    System.exit(0);
                //add party goer
                if (choice==1)
                {
                    //Room garden = roomarr[roomarr.length-1];
                    String n = keyboard.nextLine();
                    System.out.print("Please provide a name: ");
                    n = keyboard.nextLine().trim();
                    System.out.print("Please provide an intelligence level: ");
                    int wit = Integer.parseInt(keyboard.nextLine().trim());
                    if (wit<0)
                    {
                        System.out.println("Setting intelligence to 0...");
                        wit = 0;
                    }
                    if (wit>10)
                    {
                        System.out.println("Setting intelligence to 10...");
                        wit = 10;
                    }
                    System.out.print("Please provide a homicidal level: ");
                    int hl = keyboard.nextInt();
                    if (hl<0 && hl!=-10)
                    {
                        System.out.println("Setting homicidal level to 0...");
                        hl = 0;
                    }
                    if (hl>10)
                    {
                        System.out.println("Setting homicidal level to 10...");
                        hl = 10;
                    }
                    System.out.print("Please provide a creepiness level: ");
                    int cl = keyboard.nextInt();
                    if (cl<0)
                    {
                        System.out.println("Setting creepiness level to 0...");
                        cl = 0;
                    }
                    if (cl>10)
                    {
                        System.out.println("Setting creepiness level to 10...");
                        cl = 10;
                    }
                    String tool = keyboard.nextLine();
                    System.out.print("Please provide a tool: ");
                    tool = keyboard.nextLine();
                    tool.trim();
                    System.out.print("Please provide a tool danger level: ");
                    int tdl = keyboard.nextInt();
                    if (tdl<0)
                    {
                        System.out.println("Setting tool danger level to 0...");
                        tdl = 0;
                    }
                    if (tdl>10)
                    {
                        System.out.println("Setting tool danger level to 10...");
                        tdl = 10;
                    }
                    PartyGoer a = new PartyGoer(n,wit,hl,cl,tool,tdl);
                    //Simulate unlimited garden by expanding to the current number of people
                    garden.expand();
                    PartyGoer[] tmp = new PartyGoer[pgarr.length+1];
                    for (int i=0;i<pgarr.length;i++)
                    {
                        tmp[i] = pgarr[i];
                    }
                    tmp[tmp.length-1]=a;
                    pgarr = tmp;
                }
                if (choice==2)
                {
                    String n = keyboard.nextLine();
                    System.out.print("Please provide a room name: ");
                    n = keyboard.nextLine().trim();
                    System.out.print("Please provide a maximum room capacity: ");
                    int rc = Integer.parseInt(keyboard.nextLine().trim());
                    if (rc<0)
                    {
                        System.out.println("Setting maximum room capapcity to 0...");
                        rc = 0;
                    }
                    Room r = new Room(n,rc);
                    Room[] tmp = new Room[roomarr.length+1];
                    for (int i=0;i<roomarr.length;i++)
                    {
                        tmp[i] = roomarr[i];
                    }
                    tmp[tmp.length-1]=r;
                    roomarr = tmp;

                }
                if (choice==3)
                {
                    for (int i=0;i<pgarr.length;i++)
                    {
                        System.out.println(pgarr[i].toString());
                    }
                }
                if (choice==4)
                {
                    for (int i=0;i<roomarr.length;i++)
                    {
                        System.out.println(roomarr[i].toString());
                    }
                    System.out.println(garden.getRoomName()+"\n"+"RoomCap: Infinity"+"\n"+"OccupantAmt: "+garden.getOccupantAmt());
                }
                if (choice==5)
                {
                    //SimRunner sim = new SimRunner();
                    sim.play();
                }
                else
                {
                    System.out.println("Please select a valid option.");
                }
            }
        }
    private void play()
        {
            System.out.println("Round 0:");
            roundZero();
            int rnd = 1;
            System.out.println("\nThe initial murder round: ");
            //murderRound();
            while (true)
            {
                //ASK SADIA
                if ((rnd-1) % 4 == 0)
                {
                    System.out.println("Murder time...: ");
                    murderRound();
                    gameOverCheck();
                }
                System.out.println("\nMoving around...: ");
                movingRound(rnd);

                gameOverCheck();

                System.out.println("\nQuestioning...: ");
                questioningRound(rnd);

                gameOverCheck();

                rnd++;
		System.out.println("\nRound number "+rnd+"\n");
                //System.out.println("\nMoving round: ");
                //movingRound(rnd);
            }
        }
    private void gameOverCheck()
        {
            if (gameOver())
            {
                System.out.println("Game over!");
                System.exit(1);
            }
        }
    private boolean gameEndOne()
        {
            PartyGoer[] temp;
            Room[] allrooms = new Room[roomarr.length+1];
            for (int x=0;x<roomarr.length;x++)
            {
                allrooms[x] = roomarr[x];
            }
            allrooms[allrooms.length-1] = garden;
            //if no more investigators are left alive
            for (int i=0;i<allrooms.length;i++)
            {
                temp = allrooms[i].getInvestigators();
                for (int j=0;j<temp.length;j++)
                {
                    if (temp[j]!=null)
                    {
                        if (temp[j].isAlive())
                            return false;
                    }
                }
            }
            System.out.println("No more investigators are left alive");
            return true;
        }
    private boolean gameEndTwo()
        {
            PartyGoer[] temp;
            PartyGoer killer = null;;
            int murdererCounter = 0;
            int innocentCounter = 0;
        
            Room[] allrooms = new Room[roomarr.length+1];
            for (int x=0;x<roomarr.length;x++)
            {
                allrooms[x] = roomarr[x];
            }   
            allrooms[allrooms.length-1] = garden;
            //if there is only one murderer left in the house
            for (int i=0;i<allrooms.length;i++)
            {
                temp = allrooms[i].getPartyGoersInRoom();
                for (int j=0;j<temp.length;j++)
                {
                    if (temp[j]!=null)
                    {
                        //living murderers
                        if (temp[j].isAlive() && temp[j].isGuilty())
                        {
                            killer = temp[j];
                            murdererCounter++;
                        }
                    }
                }
            }
            //if there is only one murderer left alive and all rooms contain a corpse (perhaps except room that murderer is in)
            if (killer==null)
            {
                return false;
            }
            else if ((murdererCounter==1) && allRoomsContainCorpse(killer,killer.getLocation()) && killer.getLocation().getNumPeople()==1 && killer.getLocation().equals(garden)==false)
            {
                System.out.println("There is only one murderer left in the house - everyone else has either been killed, put in the garden, or stuck in the same room with no one able to do anything.");
                return true;
            }
            else
            {
                return false;
            }
        }
    private boolean allRoomsContainCorpse(PartyGoer killer,Room killerRoom)
        {
            for (int i=0;i<roomarr.length;i++)
            {
                //if the room does not contain and a corpse and the room is not the killer's room
                if (roomarr[i].containsCorpse()==false && roomarr[i].equals(killerRoom)==false)
                {
                    return false;
                }
            }
            return true;
        }
    private boolean gameEndThree()
        {
            PartyGoer[] temp;
            Room[] allrooms = new Room[roomarr.length+1];
            for (int x=0;x<roomarr.length;x++)
            {
                allrooms[x] = roomarr[x];
            }
            allrooms[allrooms.length-1] = garden;
            //No one in the house has done it after a round of questioning; all criminals have been busted and no one wants to try again
            for (int i=0;i<allrooms.length;i++)
            {
                temp = allrooms[i].getPartyGoersInRoom();
                for (int j=0;j<temp.length;j++)
                {
                    if (temp[j]!=null)
                    {
                        if (temp[j].isGuilty() && temp[j].isAlive() && temp[j].isBusted()==false)
                            return false;
                    }
                }
            }
            System.out.println("No one in the house has done it/all criminals have been busted and no one wants to try again");
            return true;
        }
    private boolean gameOver()
        {
            if (gameEndOne() || gameEndTwo() || gameEndThree())  
                return true;
            else
                return false;
        }
    private void questioningRound(int roundNumber)
        {
            questioning(roundNumber);
        }
    private void questioning(int roundNumber)
        {
            PartyGoer[] pgsinroom;
            PartyGoer[] investigators;
            PartyGoer[] questioned;
            PartyGoer cursuspect;
            int questionedCount = 0;
        
            Room[] allrooms = new Room[roomarr.length+1];
            for (int x=0;x<roomarr.length;x++)
            {
                allrooms[x] = roomarr[x];
            }
            allrooms[allrooms.length-1] = garden;
        
            for (int i=0;i<allrooms.length;i++)
            {
                pgsinroom = allrooms[i].getPartyGoersInRoom();
                investigators = allrooms[i].getInvestigators();
                questioned = new PartyGoer[pgarr.length];

                for (int j=0;j<investigators.length;j++)
                {
                    for (int n=0;n<3;n++)
                    {
                        if (investigators[j]==null)
                            n = 4;
                        else
                        {
                            cursuspect = findMostSuspicious(investigators[j].getLocation(),investigators[j],questioned);
                            if (cursuspect == null)
                                System.out.print("");
                            else if (cursuspect.isGuilty())
                            {
                                cursuspect.incrementNumInterrogations();
                                questioned[questionedCount] = cursuspect;
                                questionedCount++;
                                int iwits = investigators[j].getWits();
                                int itdl = investigators[j].getTDL();
                                //if investigator is smart enough to bust murderer
                                if ( (iwits+roundNumber+itdl) > (cursuspect.getWits()+cursuspect.getTDL()))
                                {
                                    cursuspect.getLocation().decrNumPPL();
                                    cursuspect.bustPartyGoer();
                                    System.out.println(investigators[j].getName()+" questions "+cursuspect.getName()+"."+" He busts them!");
				   // + " This is because the investigator's wits "+iwits+" plus the round number "+roundNumber+" plus their tool damage level "+itdl+" > "+cursuspect.getName()+"'s" + " wit level "+cursuspect.getWits()+" plus their tool damage level "+cursuspect.getTDL());
                                    PartyGoer[] newpgarr = new PartyGoer[pgarr.length];
                                    //get rid of dead character
                                    for (int k=0;k<pgarr.length;k++)
                                    {
                                        if (pgarr[k]==null)
                                            System.out.print("");
                                        else if (pgarr[k].equals(cursuspect))
                                            System.out.print("");
                                        else
                                            newpgarr[k] = pgarr[k];
                                    }
                                    pgarr = newpgarr;
                                }
                                else
                                {
                                    //investigator was not smart enough to bust murderer
                                    System.out.println(investigators[j].getName()+" questions "+cursuspect.getName()+","+" but is not smart enough to bust them!");
                                }
                            }
                            
                            else if (cursuspect.isGuilty()==false)
                            {
                                System.out.println(investigators[j].getName()+" questions "+cursuspect.getName()+","+" but finds them innocent!");
                                cursuspect.incrementNumInterrogations();
                                questioned[questionedCount] = cursuspect;
                                questionedCount++;
                            }
                        }
                    }
                }
            }
        }
    
    private boolean alreadyQuestioned(PartyGoer suspect,PartyGoer[] questioned)
        {
            for (int i=0;i<questioned.length;i++)
            {
                if (questioned[i]==null)
                    System.out.print("");
                else if (questioned[i].equals(suspect))
                    return true;
            }
            return false;
        }
    private PartyGoer findMostSuspicious(Room r, PartyGoer i, PartyGoer[] questioned)
        {
            int cursuspect;
            int largest = -100;
            PartyGoer ret = null;
            for (int x=0;x<r.getRoomCap();x++)
            {
                if (r.getPG(x)==null)
                {
                    System.out.print("");
                }
                //if the most suspicious is the investigator themselves or the partygoer has been questioned already
                else if(r.getPG(x).equals(i) || alreadyQuestioned(r.getPG(x),questioned))
                {
                    System.out.print("");
                }
                else
                {
                    cursuspect = r.getPG(x).getCL()-r.getPG(x).getNumInterrogations();
                    if (cursuspect>largest)
                    {
                        largest = cursuspect;
                        ret = r.getPG(x);
                    }
                }
            }
            return ret;

        }
    private void murderRound()
        {
            PartyGoer dumbest;
            PartyGoer[] pgsinroom;
            for (int i=0;i<roomarr.length;i++)
            {
                pgsinroom = roomarr[i].getPartyGoersInRoom(); 
                for (int j=0;j<pgsinroom.length;j++)
                {
                    if (pgsinroom[j]==null)
                        System.out.print("");
                    else
                    {
                        //if our attempted murder meets the first formula and he/she/it is not the only one in the room, and he/she/it is not in the garden
                        if ( (pgsinroom[j].getHL()*pgsinroom[j].getTDL() >= 20*(pgsinroom[j].getLocation().getNumPeople()-1)) && (pgsinroom[j].getLocation().getNumPeople()>1) && (pgsinroom[j].getLocation().equals(garden)==false))
                        {
                            //find the dumbest person in the room with the killer (pgsinroom[j])
                            dumbest = findDumbestInRoom(pgsinroom[j].getLocation(),pgsinroom[j]);
                            if ( (pgsinroom[j].getHL()+pgsinroom[j].getTDL()) > dumbest.getWits()+dumbest.getTDL() )
                            {
                                dumbest.getLocation().setCorpse();
                                dumbest.killPartyGoer();
                                pgsinroom[j].setGuilty();
                                PartyGoer[] newpgarr = new PartyGoer[pgarr.length];
                                //get rid of dead character from master party goer array
                                for (int k=0;k<pgarr.length;k++)
                                {
                                    if (pgarr[k]==null)
                                        System.out.print("");
                                    else if (pgarr[k].isAlive()==false)
                                        System.out.print("");
                                    else
                                        newpgarr[k] = pgarr[k];
                                }
                                System.out.println("Party Goer "+dumbest.getName()+" has been killed in the "+dumbest.getLocation().getRoomName()+" by "+pgsinroom[j].getName()+" using "+pgsinroom[j].getTool());
				// + " because the killer's homicidal level " + pgsinroom[j].getHL() + " plus their tool damage level " + pgsinroom[j].getTDL() + " > " + " the dumbest person's wits " + dumbest.getWits() + " plus their tool damage level " + dumbest.getTDL());
                                pgarr = newpgarr;
                                //terminate this inner loop because only one murder may happen in a room at a time
                                j = pgsinroom.length+1;
                            }
                        }
                    }
                }
            }
        }
    private PartyGoer findDumbestInRoom(Room r,PartyGoer attmurderer)
        {
            int curintel;
            int smallest = 100;
            PartyGoer[] pgsinroom = r.getPartyGoersInRoom();
            PartyGoer ret = null;
            for (int i=0;i<pgsinroom.length;i++)
            {
                if (pgsinroom[i]==null)
                {
                    System.out.print("");
                }
                else if(pgsinroom[i].equals(attmurderer))
                {
                    System.out.print("");
                }
                else
                {
                    curintel = pgsinroom[i].getWits();
                    if (curintel<smallest)
                    {
                        smallest = curintel;
                        ret = pgsinroom[i];
                    }
                }
            }
            return ret;
        }
    private void roundZero()
        {
            movingRound(0);
        }
    private void movingRound(int roundNumber)
        {
            shiftRooms(roundNumber);
            /*for (int i=0;i<pgarr.length;i++)
            {
                if (pgarr[i]==null)
                    System.out.print("");
                else if (pgarr[i].isAlive())
                    System.out.println((pgarr[i].getName())+" goes to the "+pgarr[i].getLocation().getRoomName());
            }*/
        }
    private boolean shiftRooms(int roundNumber)
        {
            int index;
            int numrooms = roomarr.length;
            PartyGoer pg;
            Room pgcurroom;
            Room newroom;
            if (roundNumber > 0)
            {
                for (int p=0;p<pgarr.length;p++)
                {
                    pg = pgarr[p];
                    if (pg==null)
                        System.out.print("");
                    else if (pg.isAlive() && pg.isBusted()==false)
                    {
                        pgcurroom = pg.getLocation();
                        pgcurroom.removePartyGoerFromRoom(pg);
                    }
                }
            }
           
            for (int p=0;p<pgarr.length;p++)
            {
                pg = pgarr[p];
                if (pg==null)
                    System.out.print("");
                else if (pg.isAlive() && pg.isBusted()==false)
                {
                    index = (pg.getCL()*roundNumber+pg.getWits())%numrooms;
                    newroom = roomarr[index];
                        
                    if (newroom.containsCorpse() || newroom.getNumPeople()>=newroom.getRoomCap())
                    {
                        tryDifferentRoom(index,numrooms,pg);
                    }
                    else if (newroom.getNumPeople()<newroom.getRoomCap())
                    {
                        System.out.println(pg.getName() + " goes to " + newroom.getRoomName());
			// + " because their creepiness level " + pg.getCL() + " times the round number " + roundNumber + " plus their wit " + pg.getWits() + " mod the number of rooms " + numrooms + " = " + index + " (the index of the "+newroom.getRoomName()+")"); 
			pg.setLocation(newroom);
                        newroom.addPartyGoerToRoom(pg);
                    }
                }
            }
            return true;
        }
    private boolean tryDifferentRoom(int index,int numrooms,PartyGoer pg)
        {
            //search for room with space sequentially, starting at the previously tried index
            for (int i=index;i<numrooms;i++)
            {
                if (roomarr[i].getNumPeople() < roomarr[i].getRoomCap() && roomarr[i].containsCorpse()==false)
                {
		    System.out.println(pg.getName() + " goes to " + roomarr[i].getRoomName() + " because this is the next available room.");
                    roomarr[i].addPartyGoerToRoom(pg);
                    pg.setLocation(roomarr[i]);
                    return true;
                }
            }
            //try again from start and search up to the already tried index
            for (int i=0;i<index;i++)
            {
                if (roomarr[i].getNumPeople() < roomarr[i].getRoomCap() && roomarr[i].containsCorpse()==false)
                {
		    System.out.println(pg.getName() + " goes to " + roomarr[i].getRoomName() + " because this is the next available room.");
                    roomarr[i].addPartyGoerToRoom(pg);
                    pg.setLocation(roomarr[i]);
                    return true;
                }
            }
            //if there is no space left in any room, put them in the garden
            garden.addPartyGoerToRoom(pg);
            pg.setLocation(garden);
	    System.out.println(pg.getName() + " goes to the garden because there is no more room left anywhere.");
            return true;
        }
    private PartyGoer[] parsePartyGoers()
        {
            try {
                BufferedReader in = new BufferedReader(new FileReader("suspects.dat"));
                String l = in.readLine();
                String[] amts = l.split("\\s+");
                int roomamt = Integer.parseInt(amts[0]);
                int pgamt = Integer.parseInt(amts[1]);
                if (roomamt<=0 || pgamt<=0)
                {
                    System.out.println("Please provide a value greater than 0 for the room amount and party goer amount.");
                    System.out.println("...we would have a very boring simulation otherwise.");
                    System.exit(2);
                }
                l = "";
                
                for (int i=0;i<roomamt;i++)
                {
                    in.readLine();
                }
                for (int i=0;i<pgamt;i++)
                {
                    l += in.readLine();
                    l.trim();
                    l += "\n";
                }
                String[] pgs = l.split("\\n");
                String workstr;
                String pgname;
                int wits;
                int hl;
                int cl;
                String tool;
                int tdl;
                String[] tmp;
                PartyGoer[] pgarr = new PartyGoer[pgamt];
                
                for (int i=0;i<pgamt;i++)
                {
                    workstr = pgs[i].toString();
                    tmp = workstr.split("\\|");
                    pgname = tmp[0];
                    wits = Integer.parseInt(tmp[1].trim());
                    hl = Integer.parseInt(tmp[2].trim());
                    cl = Integer.parseInt(tmp[3].trim());
                    tool = tmp[4];
                    tdl = Integer.parseInt(tmp[5].trim());
                    if (wits<0)
                    {
                        System.out.println("Setting intelligence for "+pgname+ " to 0...");
                        wits = 0;
                    }
                    if (wits>10)
                    {
                        System.out.println("Setting intelligence for "+pgname+ " to 10...");
                        wits = 10;
                    }
                    if (hl<0 && hl!=-10)
                    {
                        System.out.println("Setting homicidal level for "+pgname+ " to 0...");
                        hl = 0;
                    }
                    if (hl>10)
                    {
                        System.out.println("Setting homicidal level for "+pgname+ " to 10...");
                        hl = 10;
                    }
                    if (cl<0)
                    {
                        System.out.println("Setting creepiness level for "+pgname+ " to 0...");
                        cl = 0;
                    }
                    if (cl>10)
                    {
                        System.out.println("Setting creepiness level for "+pgname+ " to 10...");
                        cl = 10;
                    }
                    if (tdl<0)
                    {
                        System.out.println("Setting tool danger level for "+pgname+ " to to 0...");
                        tdl = 0;
                    }
                    if (tdl>10)
                    {
                        System.out.println("Setting tool danger level for "+pgname+ " to 10...");
                        tdl = 10;
                    }
                    pgarr[i] = new PartyGoer(pgname,wits,hl,cl,tool,tdl);
                }
                in.close();
                return pgarr;
            }
            catch (IOException e) 
            {
                System.out.println("Error reading file suspects.dat!");
                System.exit(1);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Room amount or party goer amount stated does not match amount actually given. Please fix your suspects.dat file and try again.");
                System.exit(1);
            }
            catch (StringIndexOutOfBoundsException e)
            {
                System.out.println("Room amount or party goer amount stated does not match amount actually given. Please fix your suspects.dat file and try again.");
                System.exit(1);
            }
            catch (Exception e)
            {
                System.out.println("Please fix the input file and try again.");
                System.exit(1);
            }
            return null;
        
        }
    private Room[] parseRooms()
        {
            try {
                BufferedReader in = new BufferedReader(new FileReader("suspects.dat"));
                String l;
                l = in.readLine();
                String[] amts = l.split("\\s+");
                int roomamt = Integer.parseInt(amts[0].trim());
                int pgamt = Integer.parseInt(amts[1].trim());
                if (roomamt<=0 || pgamt<=0)
                {
                    System.out.println("Please provide a value greater than 0 for the room amount and party goer amount.");
                    System.out.println("...we would have a very boring simulation otherwise.");
                    System.exit(2);
                }
                l="";
            
                for (int i=0;i<roomamt;i++)
                {
                    l += in.readLine();
                    //clean the end of the line (any extra whitespace)
                    l.trim();
                    //replace the line break
                    l += "\n";
                    
                }
                //clean the added line break
                l.trim();
                //split into an array using the line break as a delimiter
                String[] rooms = l.split("\\n");
                String roomname = "";
                String workstr;
                String roomcapstr;
                int roomcap;
            
                Room[] roomlist = new Room[roomamt];
                for (int i=0;i<roomamt;i++)
                {
                    workstr = rooms[i].toString();
                    roomname = workstr.substring(0,workstr.indexOf('.'));
                    workstr = rooms[i].toString();
                    
                    roomcapstr = (workstr.substring(workstr.indexOf('.')+1,workstr.length())).trim();
                    roomcap = Integer.parseInt(roomcapstr);
                    if (roomcap<2)
                    {
                        System.out.println("Setting room capacity for "+roomname+" to 2...");
                        roomcap = 2;
                    }
                    roomlist[i] = new Room(roomname,roomcap);
                }
                in.close();
                return roomlist;
            }
            catch (IOException e) 
            {
                System.out.println("Error reading file suspects.dat!");
                System.exit(1);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Room amount or party goer amount stated does not match amount actually given. Please fix your suspects.dat file and try again.");
                System.exit(1);
            }
            catch (StringIndexOutOfBoundsException e)
            {
                System.out.println("Room amount or party goer amount stated does not match amount actually given. Please fix your suspects.dat file and try again.");
                System.exit(1);
            }
            catch (Exception e)
            {
                System.out.println("Please fix the input file and try again.");
                System.exit(1);
            }
            return null;
        }
    
}
