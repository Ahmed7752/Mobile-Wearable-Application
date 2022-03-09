package androidev.main_app;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import androidx.annotation.Nullable;

        import java.util.ArrayList;
        import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Tables
    public static final String USER_TABLE = "USER_TABLE";
    public static final String VOTES_TABLE = "VOTES_TABLE";
    public static final String RESULTS_TABLE = "RESULTS_TABLE";
    public static final String ADMIN_TABLE = "ADMIN_TABLE";
    public static final String QUESTIONS_TABLE = "QUESTIONS_TABLE";
    public static final String SAVED_POLL_TABLE = "SAVED_POLL_TABLE";
    public static final String POLL_TABLE = "POLL_TABLE";

    //common columns for all tables
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_VOTE = "VOTE";
    public static final String COLUMN_POLL_ID = "POLL_ID";
    public static final String COLUMN_QUESTION_ID = "QUESTION_ID";
    public static final String COLUMN_COMPLETE = "COMPLETE";

    //Common columns for user and admin tables
    public static final String COLUMN_POSTCODE = "POSTCODE";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    //User table columns
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_AGE = "AGE";
    public static final String COLUMN_NUMBER = "NUMBER";
    public static final String COLUMN_ETHNICITY = "ETHNICITY";
    public static final String COLUMN_INTERESTS = "INTERESTS";
    public static final String COLUMN_POLITICAL_INTERESTS = "POLITICAL_INTERESTS";


    //Poll table columns
    public static final String COLUMN_POLL_TITLE = "TITLE";
    public static final String COLUMN_POLL_TOPIC = "TOPIC";
    public static final String COLUMN_POLL_FREQUENCY = "FREQUENCY";
    public static final String COLUMN_TOTAL_LIKES = "TOTAL_LIKES";


    //Question table columns
    public static final String COLUMN_QUESTION_TITLE = "TITLE";
    public static final String COLUMN_AGREE_COUNT = "AGREE_COUNT";
    public static final String COLUMN_DISAGREE_COUNT = "DISAGREE_COUNT";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "18124225.db", null, 1);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create user table
        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_POSTCODE + " TEXT, " + COLUMN_USERNAME + " TEXT UNIQUE, " + COLUMN_EMAIL + " TEXT UNIQUE, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_AGE + " TEXT, " + COLUMN_NUMBER + " TEXT, " + COLUMN_INTERESTS + " TEXT, " + COLUMN_POLITICAL_INTERESTS + " TEXT,  " + COLUMN_ETHNICITY + " TEXT, " + COLUMN_COMPLETE + " INT )";
        //Create admins table
        String createAdminTableStatement = "CREATE TABLE " + ADMIN_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMAIL + " INT, " + COLUMN_PASSWORD + " INT )";
        //Create poll table
        String createPollTableStatement = "CREATE TABLE " + POLL_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_POLL_TITLE + " TEXT, " + COLUMN_POLL_TOPIC + " TEXT, " + COLUMN_POLL_FREQUENCY + " TEXT, " + COLUMN_TOTAL_LIKES + " INT )";
        //Create question table
        String createQuestionsTableStatement = "CREATE TABLE " + QUESTIONS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_POLL_ID + " INT, "  + COLUMN_QUESTION_TITLE + " TEXT, " + COLUMN_AGREE_COUNT+ " TEXT, "  + COLUMN_DISAGREE_COUNT+ " TEXT )";
        //Create saved polls table
        String createSavedTableStatement = "CREATE TABLE " + SAVED_POLL_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_POLL_ID + " INT, " + COLUMN_USER_ID + " INT, " + COLUMN_COMPLETE + " INT )";
        //Create votes table for questions
        String createVotesTableStatement = "CREATE TABLE " + VOTES_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_QUESTION_ID + " INT, " + COLUMN_VOTE + " TEXT, " + COLUMN_USER_ID + " INT, " + COLUMN_POLL_ID + " INT )";
        //Create result table for polls
        String createResultsTableStatement = "CREATE TABLE " + RESULTS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_POLL_TITLE + " INT, "  + COLUMN_USER_ID + " INT, " + COLUMN_POSTCODE + " TEXT, "  + COLUMN_VOTE+ " TEXT, " + COLUMN_POLL_TOPIC + " INT )";

        //Execute SQL statement
        db.execSQL(createQuestionsTableStatement);
        db.execSQL(createPollTableStatement);
        db.execSQL(createUserTableStatement);
        db.execSQL(createAdminTableStatement);
        db.execSQL(createSavedTableStatement);
        db.execSQL(createVotesTableStatement);
        db.execSQL(createResultsTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Remove values from database//

    public int deleteUser(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        int rows;
        rows = db.delete(USER_TABLE, "ID = ?", new String[]{String.valueOf(id)});
        return rows;
    }

    public void deleteVotingHistory(String id){

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(RESULTS_TABLE, "USER_ID = ?", new String[]{id});

    }

    public void deleteAllFavourites(String id){

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(SAVED_POLL_TABLE, "USER_ID = ?", new String[]{id});

    }



    //Methods to fetch values from database//

    //Fetch one users details from the user table where the ID column equals the current user
    // and store in a array list as objects,
    // using the methods from the "ProfileCompletionModel" class.
    public List<ProfileCompletionModel> getUserDetails(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from USER_TABLE where ID= ? ",  new String[]{id});

        ArrayList<ProfileCompletionModel> models = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String age = cursor.getString(7);
                String phoneNumber = cursor.getString(8);
                String fName = cursor.getString(1);
                String lName = cursor.getString(2);
                String postcode = cursor.getString(3);
                String interests = cursor.getString(9);
                String political = cursor.getString(10);
                String ethnicity = cursor.getString(11);

                ProfileCompletionModel userData = new ProfileCompletionModel(age,phoneNumber,fName,lName,postcode,interests,political,ethnicity);
                models.add(userData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }

    //Fetch all users from the user table and store in a array list as objects,
    // using the methods from the "UserModel" class.
    public List<UserModel> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from USER_TABLE ", null);

        ArrayList<UserModel> models = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(0);
                String username = cursor.getString(4);
                String email = cursor.getString(5);
                String password = cursor.getString(6);

                UserModel newUser = new UserModel(userID,username,email,password);
                models.add(newUser);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }


    //Fetch all questions from the question table and store in a array list as objects,
    // using the methods from the "QuestionsModel" class.
    public List<QuestionsModel> getPollQuestions(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from QUESTIONS_TABLE where POLL_ID=? ", new String[]{id});

        ArrayList<QuestionsModel> models = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int questionID = cursor.getInt(0);
                int pollID = cursor.getInt(1);
                String title = cursor.getString(2);


                QuestionsModel newQuestion = new QuestionsModel(questionID,pollID,title);
                models.add(newQuestion);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }

    //Fetch all polls from the poll table and store in a array list as objects,
    // using the methods from the "PollModel" class.
    public List<VoteModel> getAllVotes( String ID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from VOTES_TABLE where USER_ID =? ", new String[]{ID});

        ArrayList<VoteModel> models = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int voteID = cursor.getInt(0);
                int questionID = cursor.getInt(1);
                String vote = cursor.getString(2);
                int userID = cursor.getInt(3);
                int pollID = cursor.getInt(3);


                VoteModel newVote = new VoteModel(pollID, userID, vote, voteID,questionID);
                models.add(newVote);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }

    //Fetch all polls from the poll table and store in a array list as objects,
    // using the methods from the "PollModel" class.
    public List<PollModel> getAllPolls() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from POLL_TABLE ", null);

        ArrayList<PollModel> models = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int pollID = cursor.getInt(0);
                String title = cursor.getString(1);
                String topic = cursor.getString(2);
                int totalLikes = cursor.getInt(3);

                PollModel newPoll = new PollModel(pollID, title, topic, totalLikes);
                models.add(newPoll);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }

    //Fetch all polls from the saved polls table when given the id of the saved poll and store in a array list as objects,
    // using the methods from the "SavedPollModel" class.
    public List<String> getSavedPolls(String userID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from SAVED_POLL_TABLE ", new String[]{userID});

        ArrayList<String> models = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int pollID = cursor.getInt(1);


                models.add(String.valueOf(pollID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }

    //Fetch all polls from the saved polls table when given the id of the saved poll and store in a array list as objects,
    // using the methods from the "SavedPollModel" class.
    public List<PollModel> getPolls(String UserID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM POLL_TABLE WHERE ID IN (SELECT POLL_ID FROM SAVED_POLL_TABLE WHERE USER_ID= ?)", new String[]{UserID});

        ArrayList<PollModel> models = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int pollID = cursor.getInt(0);
                String title = cursor.getString(1);
                String topic = cursor.getString(2);
                int totalLikes = cursor.getInt(3);


                PollModel poll = new PollModel(pollID,title,topic,totalLikes);
                models.add(poll);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }

    //Fetch only the polls that have a certain <String> value in the TOPIC column, and store it
    // as objects using the methods from the "PollModel" class.
    public List<PollModel> getAllPollInterest(String interest) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from POLL_TABLE where TOPIC=?", new String[]{interest});

        ArrayList<PollModel> models = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int pollID = cursor.getInt(0);
                String title = cursor.getString(1);
                String topic = cursor.getString(2);
                int totalLikes = cursor.getInt(3);

                PollModel poll = new PollModel(pollID,title,topic,totalLikes);
                models.add(poll);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }
    //Fetch only the polls that have a certain <String> value in the TOPIC column, and store it
    // as objects using the methods from the "PollModel" class.
    public List<ResultsModel> getResultsPollInterest(String interest) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from RESULTS_TABLE where TOPIC=?", new String[]{interest});

        ArrayList<ResultsModel> models = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int resultID = cursor.getInt(0);
                String pollTitle = cursor.getString(1);
                int userID = cursor.getInt(2);
                String postcode = cursor.getString(3);
                String vote = cursor.getString(4);
                String topic = cursor.getString(5);

                ResultsModel poll = new ResultsModel(resultID,pollTitle,userID,vote,postcode,topic);
                models.add(poll);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }


    //Fetch only the polls that have a certain <String> value in the TOPIC column, and store it
    // as objects using the methods from the "PollModel" class.
    public List<PollModel> getAllRequestedPolls(String interest, String frequency) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from POLL_TABLE where TOPIC=? and FREQUENCY=?", new String[]{interest,frequency});

        ArrayList<PollModel> models = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int pollID = cursor.getInt(0);
                String title = cursor.getString(1);
                String topic = cursor.getString(2);
                int totalLikes = cursor.getInt(3);

                PollModel poll = new PollModel(pollID,title,topic,totalLikes);
                models.add(poll);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return models;
    }

    //Fetch the value from the INTERESTS column in user table where the id equals a given id,
    //then return a string containing the results from the cursor.
    public String findInterests(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_TABLE where ID=?", new String[]{id});
        String str = cursor.toString();
        if(cursor.moveToLast())
            str  =  cursor.getString( cursor.getColumnIndex("INTERESTS") );
        cursor.close();
        return str;

    }



    //Fetch the id of a user in user table where values of the EMAIL and PASSWORD columns
    //equal given email and password <Strings>,
    //then return a string containing the results from the cursor.
    public String findUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_TABLE where EMAIL=? and PASSWORD=?", new String[]{email,password});
        String str = cursor.toString();
        if(cursor.moveToLast())
            str  =  cursor.getString( cursor.getColumnIndex("ID") );
        cursor.close();
        return str;

    }

    //Fetch the id of a user in user table where values of the EMAIL and PASSWORD columns
    //equal given email and password <Strings>,
    //then return a string containing the results from the cursor.
    public String findUserPostcode(String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_TABLE where ID=?", new String[]{userID});
        String str = cursor.toString();
        if(cursor.moveToLast())
            str  =  cursor.getString( cursor.getColumnIndex("POSTCODE") );
        cursor.close();
        return str;

    }

    //Fetch the id of the last entered row of the user table and return a string contain the id.
    public String findID(){
        SQLiteDatabase db = this.getReadableDatabase();
        String findLastEnteredID = "SELECT * FROM USER_TABLE";
        Cursor cursor = db.rawQuery(findLastEnteredID, null);
        String str = "";
        if(cursor.moveToLast())
            str  =  cursor.getString( cursor.getColumnIndex("ID") );
        return str;

    }

    //Fetch the id of a row where the POLL_TITLE column contains a given <String> and
    // return a <String> containing a result from the cursor.
    public String findPollID(String pollTitle){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM POLL_TABLE WHERE TITLE=?", new String[]{pollTitle});
        String str = "";
        if(cursor.moveToLast())
            str  =  cursor.getString( cursor.getColumnIndex("ID") );
        return str;

    }


    //Methods to check values from the database//

    //Check if the user table contains a given <String> in the EMAIL column, return a true or
    // false depending on if the row exits (a user with that email is already in the table)
    public Boolean CheckVote(String questionID,String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from VOTES_TABLE where QUESTION_ID=? and USER_ID=?", new String[]{questionID,userID});
        if (cursor.getCount() >0){
            cursor.close();
            return true;
        }
        else{
            return false;
        }

    }
    //Check if the user table contains a given <String> in the EMAIL column, return a true or
    // false depending on if the row exits (a user with that email is already in the table)
    public Boolean CheckResult(String pollTitle,String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from VOTES_TABLE where QUESTION_ID=? and USER_ID=?", new String[]{pollTitle,userID});
        if (cursor.getCount() >0){
            cursor.close();
            return true;
        }
        else{
            return false;
        }

    }

    //Check if the user table contains a given <String> in the EMAIL column, return a true or
    // false depending on if the row exits (a user with that email is already in the table)
    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_TABLE where EMAIL=?", new String[]{email});
        if (cursor.getCount() >0){
            cursor.close();
            return false;
        }
        else{
            return true;
        }

    }

    //Check if the poll table contains a given <String> in the POLL_TITLE column, return a true or
    // false depending on if the row exits (a poll with that title is already in the table)
    public Boolean checkPoll(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from POLL_TABLE where TITLE=?", new String[]{title});
        if (cursor.getCount() >0){
            cursor.close();
            return false;
        }
        else{
            return true;
        }

    }

    //Check if the question table contains a given <String> in the QUESTION_TITLE column, return a true or
    // false depending on if the row exits (a question with that title is already in the table)
    public Boolean checkQuestions(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from QUESTIONS_TABLE where TITLE=?", new String[]{title});
        if (cursor.getCount() >0){
            cursor.close();
            return false;
        }
        else{
            return true;
        }

    }

    //Check if the user table contains a certain row when given an email and password <Strings> in
    // the EMAIL and PASSWORD columns, return a true or false depending on if the row exits
    // (The user is or is not in the table)
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_TABLE where EMAIL=? and PASSWORD=?", new String[]{email,password});
        if (cursor.getCount() >0){
            cursor.close();
            return true;
        }
        else{
            return false;
        }


    }

    //Check if the admin table contains a certain row when given an email and password <Strings> in
    // the EMAIL and PASSWORD columns, return a true or false depending on if the row exits
    // (The admin is or is not in the table)
    public Boolean adminCheckEmailPassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ADMIN_TABLE where EMAIL=? and PASSWORD=?", new String[]{email,password});
        if (cursor.getCount() >0){
            cursor.close();
            return true;
        }
        else{
            return false;
        }


    }
    //Check if the user table contains a certain user when given an id
    //then return a true or false depending on if any rows exit
    public Boolean checkUser(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_TABLE where ID=?", new String[]{id});
        if (cursor.getCount() >0){
            cursor.close();
            return true;
        }
        else{
            return false;
        }


    }


    //Check if the admin table contains an rows
    //then return a true or false depending on if any rows exit
    public Boolean checkAdmin(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select count(*) from  ADMIN_TABLE",null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if(count == 0){
            return true;
        }else {
            return false;
        }


    }

    //Check the value stored in the COMPLETE column of the user table,
    //when given the id of a row as a <String>.
    public Boolean checkComplete(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER_TABLE where ID=?", new String[]{id});
        String str = "";
        cursor.moveToLast();
        if (cursor.moveToFirst()){
            str = cursor.getString(cursor.getColumnIndex("COMPLETE"));
            if (str.contains("0")) {
                cursor.close();
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public Boolean checkPollComplete(String userID, String pollID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select COMPLETE from SAVED_VOTES_TABLE where USER_ID=? and POLL_ID", new String[]{userID,pollID});
        String str = "";
        cursor.moveToLast();
        str  =  cursor.getString( cursor.getColumnIndex("COMPLETE") );
        if (str.contains("0")){
            cursor.close();
            return false;
        }else {
            return true;
        }


    }

    //Methods to insert values into the database//

    public boolean completeVote (String vote, String postcode, int userID, String pollTitle, String topic){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_POSTCODE, postcode);
        cv.put(COLUMN_VOTE, vote);
        cv.put(COLUMN_USER_ID, userID);
        cv.put(COLUMN_POLL_TITLE, pollTitle);
        cv.put(COLUMN_POLL_TOPIC, topic);
        long insert = db.insert(RESULTS_TABLE, null, cv);

        if(insert == -1){
            return false;

        }else{
            return true;
        }
    }

    public boolean Vote (int questionID, String vote, int userID, int pollID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUESTION_ID, questionID);
        cv.put(COLUMN_VOTE, vote);
        cv.put(COLUMN_USER_ID, userID);
        cv.put(COLUMN_POLL_ID, pollID);
        long insert = db.insert(VOTES_TABLE, null, cv);

        if(insert == -1){
            return false;

        }else{
            return true;
        }
    }

    //Update a users COMPLETE column in the user table when given the users id <String>.
    //The returned value from the IsComplete class is then placed into the COMPLETE column.
    public boolean complete(IsComplete isComplete, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COMPLETE, isComplete.getComplete());

        long update = db.update(USER_TABLE, cv, "ID =?", new String[]{id});
        if(update == -1){
            return false;

        }else{
            return true;
        }

    }



    //Multiple columns of a user are updated, when given an id of the row.
    //The methods from ProfileCompletionModel and IsComplete classes are used to gather user inputs,
    //which are then placed into the corresponding columns.
    public boolean completeProfile (ProfileCompletionModel profileCompletionModel, IsComplete isComplete, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_FIRST_NAME, profileCompletionModel.getFname());
        cv.put(COLUMN_LAST_NAME, profileCompletionModel.getLname());
        cv.put(COLUMN_AGE, profileCompletionModel.getAge());
        cv.put(COLUMN_POSTCODE, profileCompletionModel.getPostcode());
        cv.put(COLUMN_NUMBER, profileCompletionModel.getPhoneNumber());
        cv.put(COLUMN_ETHNICITY, profileCompletionModel.getEthnicity());
        cv.put(COLUMN_INTERESTS, profileCompletionModel.getInterests());
        cv.put(COLUMN_POLITICAL_INTERESTS, profileCompletionModel.getPoliticalInterests());
        cv.put(COLUMN_COMPLETE, isComplete.getComplete());


        long update = db.update(USER_TABLE, cv, "ID =?", new String[]{id});
        if(update == -1){
            return false;

        }else{
            return true;
        }
    }

    //A new user is added to the user table, and the following columns are populated with values,
    //gathered my the methods from UserModel class
    public boolean addUser (UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, userModel.getUsername());
        cv.put(COLUMN_EMAIL, userModel.getEmail());
        cv.put(COLUMN_PASSWORD, userModel.getPw());
        cv.put(COLUMN_COMPLETE, "0");
        long insert = db.insert(USER_TABLE, null, cv);

        if(insert == -1){
            return false;

        }else{
            return true;
        }
    }

    //A new admin is added to the admin table, and the following columns are hard coded with values.
    public boolean addAdmin(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL, "admin@gmail.com");
        cv.put(COLUMN_PASSWORD, "123");

        long insert = db.insert(ADMIN_TABLE, null, cv);
        if(insert == -1){
            return false;

        }else{
            return true;
        }

    }

    //Add a liked poll from a user to the liked table, when given a poll id and the user id.
    public boolean savePoll(int pollID, int userID, IsComplete isComplete){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POLL_ID, pollID);
        cv.put(COLUMN_USER_ID, userID);
        cv.put(COLUMN_COMPLETE, isComplete.getComplete());

        long insert = db.insert(SAVED_POLL_TABLE, null, cv);
        if(insert == -1){
            return false;

        }else{
            return true;
        }

    }

    //A new question is added to the question table, and the following columns are populated
    //with values gathered from user inputs.
    public boolean addQuestions(String title, String pollID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION_TITLE, title);
        cv.put(COLUMN_POLL_ID, pollID);


        long insert = db.insert(QUESTIONS_TABLE, null, cv);
        if(insert == -1){
            return false;

        }else{
            return true;
        }

    }

    //A new poll is added to the poll table, and the following columns are populated
    //with values gathered from user inputs.
    public boolean addPolls(String title, String topic, String frequency){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POLL_TITLE, title);
        cv.put(COLUMN_POLL_TOPIC, topic);
        cv.put(COLUMN_POLL_FREQUENCY, frequency);

        long insert = db.insert(POLL_TABLE, null, cv);
        if(insert == -1){
            return false;

        }else{
            return true;
        }

    }

}
