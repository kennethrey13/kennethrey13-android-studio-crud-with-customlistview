package com.studentremarkscalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ListView lvItems;
    IconDataAdapter iconAdapter;
    EditText etxtSearch, etxtFname, etxtLname, etxtPG, etxtMG, etxtFG, etxtProgram;
    Button btnSave, btnSearch, btnDelete, btnUpdate, btnClear, btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etxtSearch = (EditText) findViewById(R.id.etxtSearch);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etxtFname = (EditText) findViewById(R.id.etxtFname);
        etxtLname = (EditText) findViewById(R.id.etxtLname);
        etxtPG = (EditText) findViewById(R.id.etxtPG);
        etxtMG = (EditText) findViewById(R.id.etxtMG);
        etxtFG = (EditText) findViewById(R.id.etxtFG);
        etxtProgram = (EditText) findViewById(R.id.etxtprogram);


        btnSave = (Button) findViewById(R.id.btnSave);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnAdd = (Button) findViewById(R.id.btnAddNew);


        btnClear.setEnabled(false);
        btnClear.setText("Cancel");
        updateList();

        btnSave.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        disableFields();
    }



    public void doSave(View v){
        DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
        dbHelper.openDatabase();



        StudentClass studObj = new StudentClass();
        studObj.setSid(0);
        studObj.setFirstName(etxtFname.getText().toString());
        studObj.setLastName(etxtLname.getText().toString());
        studObj.setProgram(etxtProgram.getText().toString());
        studObj.setPG(Integer.parseInt(etxtPG.getText().toString()));
        studObj.setMG(Integer.parseInt(etxtMG.getText().toString()));
        studObj.setFG(Integer.parseInt(etxtFG.getText().toString()));

        dbHelper.addNewStudent(studObj);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("SAVE RECORD");
        alert.setMessage("STUDENT RECORD SAVED");
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        alert.show();

        updateList();

        disableFields();
        btnSearch.setEnabled(true);
        etxtSearch.setEnabled(true);
        btnSave.setEnabled(false);
        btnClear.setText("");
        btnClear.setEnabled(false);
        btnAdd.setEnabled(true);
       etxtMG.setText("");
        etxtFname.setText("");
        etxtLname.setText("");
        etxtPG.setText("");
        etxtFG.setText("");
        etxtProgram.setText("");
    }


    public void  doSearch(View v){
        DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
        dbHelper.openDatabase();
        if (etxtSearch.getText().toString().equals("")){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("SQL Info");
            alert.setMessage("ID must not be null");
            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create();
            alert.show();

        }else {
            Integer idTosearch = Integer.parseInt(etxtSearch.getText().toString());
            StudentClass studObj = dbHelper.searchStudent(idTosearch);
            if (studObj != null) {
                Integer id = studObj.getSid();
                Integer PG = studObj.getPG();
                Integer MG = studObj.getMG();
                Integer FG = studObj.getFG();
                etxtSearch.setText(id.toString());
                etxtFname.setText(studObj.getFirstName().toString());
                etxtLname.setText(studObj.getLastName().toString());
                etxtProgram.setText(studObj.getProgram().toString());
                etxtPG.setText(PG.toString());
                etxtMG.setText(MG.toString());
                etxtFG.setText(FG.toString());
                btnAdd.setEnabled(false);
                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                btnClear.setText("Clear");
                btnClear.setEnabled(true);
                etxtSearch.setEnabled(false);
                enableFields();
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("SQL Info");
                alert.setMessage("No Student Found");
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                alert.show();
            }
        }

    }



    public void doUpdate(View view){
        final DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
        dbHelper.openDatabase();






        StudentClass studObj =dbHelper.searchStudent(Integer.parseInt(etxtSearch.getText().toString()));
        StudentClass stud = new StudentClass();
        stud.setFirstName(etxtFname.getText().toString());
        stud.setLastName(etxtLname.getText().toString());
        stud.setProgram(etxtProgram.getText().toString());
        stud.setPG(Integer.parseInt(etxtPG.getText().toString()));
        stud.setMG(Integer.parseInt(etxtMG.getText().toString()));
        stud.setFG(Integer.parseInt(etxtFG.getText().toString()));
        stud.setSid(Integer.parseInt(etxtSearch.getText().toString()));

        dbHelper.updateRecord(stud);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("UPDATE RECORD");
        alert.setMessage("The Student ID: " + etxtSearch.getText() + "\nUPDATED");
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        alert.show();
        updateList();





    }



    private  void enableFields(){
        etxtFname.setEnabled(true);
        etxtLname.setEnabled(true);
        etxtPG.setEnabled(true);
        etxtMG.setEnabled(true);
        etxtFG.setEnabled(true);
        etxtProgram.setEnabled(true);


    }

    private  void disableFields(){
        etxtFname.setEnabled(false);
        etxtLname.setEnabled(false);
        etxtPG.setEnabled(false);
        etxtMG.setEnabled(false);
        etxtFG.setEnabled(false);
        etxtProgram.setEnabled(false);


    }

    private void updateList() {
        DatabaseHelper dbHelper= new DatabaseHelper(getBaseContext());
        dbHelper.openDatabase();
        ArrayList<StudentClass> studentList = dbHelper.getAllStudents();


        List<String> myarray = new ArrayList<String>();
        ArrayList<IconClass> iconDlist = new ArrayList<IconClass>();


        String strTitle = "";
        String strDesc = "";

        for (StudentClass stud: studentList){
            IconClass objnew = new IconClass();
            strTitle+="ID:" + stud.getSid() + " " + stud.getLastName() + ", " + stud.getFirstName();
            strDesc+=stud.getProgram() + " AVG:" + stud.getAverage() + " " + stud.getRemarks();

            objnew.setTitleStr(strTitle);
            objnew.setDescStr(strDesc);
            objnew.setDrawableID(R.drawable.users);
            iconDlist.add(objnew);
           strTitle ="";
            strDesc ="";
        }


        iconAdapter = new IconDataAdapter(getBaseContext(), R.layout.custom_list_view, iconDlist);
        lvItems.setAdapter(iconAdapter);
        lvItems.setOnItemClickListener(new ListView.OnItemClickListener() {

                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                               // TODO Auto-generated method stub
                                               IconClass iconObj = (IconClass) parent.getItemAtPosition(position);
                                               Toast.makeText(getApplicationContext(), "Selected: " + iconObj.getTitleStr(), Toast.LENGTH_LONG).show();

                                               String data = iconObj.getTitleStr();
                                               String datafilter = data.substring(3, 5);
                                               data = datafilter.replace(" ", "");
                                               DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
                                               dbHelper.openDatabase();

                                               Integer idTosearch = Integer.parseInt(data);
                                               StudentClass studObj = dbHelper.searchStudent(idTosearch);

                                               Integer idget = studObj.getSid();
                                               Integer PG = studObj.getPG();
                                               Integer MG = studObj.getMG();
                                               Integer FG = studObj.getFG();
                                               etxtSearch.setText(idget.toString());
                                               etxtFname.setText(studObj.getFirstName().toString());
                                               etxtLname.setText(studObj.getLastName().toString());
                                               etxtProgram.setText(studObj.getProgram().toString());
                                               etxtPG.setText(PG.toString());
                                               etxtMG.setText(MG.toString());
                                               etxtFG.setText(FG.toString());
                                               btnAdd.setEnabled(false);
                                               btnSave.setEnabled(false);
                                               btnUpdate.setEnabled(true);
                                               btnDelete.setEnabled(true);
                                               btnClear.setText("Clear");
                                               btnClear.setEnabled(true);
                                               etxtSearch.setEnabled(false);
                                               btnSearch.setEnabled(false);
                                               enableFields();


                                           }

                                       }

        );


       /** ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myarray );
**/


      /**  lvItems.setAdapter(arrayAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) parent.getItemAtPosition(position);

                String datafilter = data.substring(3, 5);
                data = datafilter.replace(" ", "");
                DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
                dbHelper.openDatabase();

                Integer idTosearch = Integer.parseInt(data);
                StudentClass studObj = dbHelper.searchStudent(idTosearch);

                Integer idget = studObj.getSid();
                Integer PG = studObj.getPG();
                Integer MG = studObj.getMG();
                Integer FG = studObj.getFG();
                etxtSearch.setText(idget.toString());
                etxtFname.setText(studObj.getFirstName().toString());
                etxtLname.setText(studObj.getLastName().toString());
                etxtProgram.setText(studObj.getProgram().toString());
                etxtPG.setText(PG.toString());
                etxtMG.setText(MG.toString());
                etxtFG.setText(FG.toString());
                btnAdd.setEnabled(false);
                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                btnClear.setText("Clear");
                btnClear.setEnabled(true);
                etxtSearch.setEnabled(false);
                btnSearch.setEnabled(false);
                enableFields();

                        }

        });




        // students.setText(strLines);  **/
    }






    public void doAdd(View v){

        etxtSearch.setEnabled(false);
        btnClear.setText("Cancel");
        btnClear.setEnabled(true);
        btnSearch.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSave.setEnabled(true);
        btnAdd.setEnabled(false);
        enableFields();
    }

    public void deleteRecord(View v){

        final DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
        dbHelper.openDatabase();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("DELETE RECORD");
        alert.setMessage("Do you really Want To Delete Record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteRecord(Integer.parseInt(etxtSearch.getText().toString()));
                etxtSearch.setText("");
                doClear(null);
                updateList();
                disableFields();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();

    }


    public void doClear(View v) {
        etxtFname.setText("");
        etxtSearch.setEnabled(true);
        btnSearch.setEnabled(true);
        etxtLname.setText("");
        etxtPG.setText("");
        etxtMG.setText("");
        etxtSearch.setText("");
        etxtFG.setText("");
        etxtProgram.setText("");
        disableFields();
        btnAdd.setEnabled(true);
        btnSave.setEnabled(false);
        btnClear.setText("");
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

    }
    public class IconDataAdapter extends ArrayAdapter<IconClass> {

        private ArrayList<IconClass> iconList;
        int layoutResID;

        public IconDataAdapter(Context context, int resourceLayoutID, ArrayList<IconClass> listObj) {
            super(context, resourceLayoutID, listObj);
            // TODO Auto-generated constructor stub
            this.layoutResID = resourceLayoutID;
            this.iconList = new ArrayList<IconClass>();
            this.iconList.addAll(listObj);
        }
        @Override
        public void add(IconClass object) {
            // TODO Auto-generated method stub
            super.add(object);
            iconList.add(object);
        }

        //adding new set of arraylist (custom)
        public void addAll(ArrayList<IconClass> obj) {
            // TODO Auto-generated method stub
            iconList.clear();
            iconList.addAll(obj);
        }

        @Override
        public void remove(IconClass object) {
            // TODO Auto-generated method stub
            super.remove(object);
            iconList.remove(object);
        }

        private class ViewHolder {
            TextView title;
            TextView desc;
            ImageView imgv;
            Button btndelete;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;
            View view = convertView;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(layoutResID, null);
                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.txtTitle);
                holder.desc = (TextView) view.findViewById(R.id.txtDesc);
                holder.imgv = (ImageView) view.findViewById(R.id.imageView1);
                holder.btndelete = (Button) view.findViewById(R.id.btnDelete);
                view.setTag(holder);
                holder.btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        Button btnTag = (Button) v;
                        IconClass iconObj = (IconClass) btnTag.getTag();

                     //getting the ID
                        String data = iconObj.getTitleStr();
                        String datafilter = data.substring(3, 5);
                        data = datafilter.replace(" ", "");


                        //delete code for database
                        Toast.makeText(getApplicationContext(), iconObj.getTitleStr()+" Deleted" , Toast.LENGTH_LONG).show();
                        iconAdapter.remove(iconObj);
                        iconAdapter.notifyDataSetChanged();
                        final DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
                        dbHelper.openDatabase();
                        dbHelper.deleteRecord(Integer.parseInt(data));
                        etxtSearch.setText("");
                        doClear(null);
                        updateList();
                        disableFields();







                     //   Toast.makeText(getApplicationContext(), "Selected: " + iconObj.getTitleStr(), Toast.LENGTH_LONG).show();








                    }
                });

            } else {
                holder = (ViewHolder) view.getTag();
            }
            try {
                IconClass iconObj = iconList.get(position);
                holder.title.setText(iconObj.getTitleStr());
                holder.desc.setText(iconObj.getDescStr());
                holder.imgv.setBackgroundResource(iconObj.getDrawableID());
                holder.btndelete.setTag(iconObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return view;
        }

    }





}
