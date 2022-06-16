package id19110100.hcmute.edu.reaboadmin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id19110100.hcmute.edu.reaboadmin.Adapter.AdapterCategoryAd;
import id19110100.hcmute.edu.reaboadmin.Adapter.LibraryAdapter;
import id19110100.hcmute.edu.reaboadmin.AdminDashboardActivity;
import id19110100.hcmute.edu.reaboadmin.Model.Book;
import id19110100.hcmute.edu.reaboadmin.Model.Library;
import id19110100.hcmute.edu.reaboadmin.Model.ModelCategoryAd;
import id19110100.hcmute.edu.reaboadmin.Model.ModelPdf;
import id19110100.hcmute.edu.reaboadmin.Model.Product;
import id19110100.hcmute.edu.reaboadmin.R;

public class LibraryFragment extends Fragment {
    ArrayList<Library> libraries;
    RecyclerView cartItemRc;
    TextView total_price;
    Button btn_confirm;
    ArrayList<RecyclerView> recyclerViews;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library,container,false);
        InflateRecyclerView(view);
        firebaseAuth = FirebaseAuth.getInstance();

        loadLibrary();

        return view;
    }

    private void loadLibrary() {

        String uid = firebaseAuth.getUid();
        //init arraylist
        libraries = new ArrayList<>();
        //get all libraries from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Library");
        ref.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear arraylist before adding data into it
                libraries.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    //get data
                    Library library = ds.getValue(Library.class);

                    //add to arraylist
                    libraries.add(library);
                }

                Classify(libraries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Classify to recyclerview
    private void Classify(ArrayList<Library> libraries) {
        /*Main idea is that loop through all libraries and check the first char in tittle belong to which collection,
        then create adapter for each collection and add to recycler view*/
        List<LibraryAdapter> adapterList = new ArrayList<>();
        ArrayList<ArrayList<Library>> arrayLists = new ArrayList<ArrayList<Library>> ();

        for(Integer i = 0; i < 27; i ++) {
            ArrayList<Library> list = new ArrayList<>();
            arrayLists.add(list);
        }

        for (Library library : libraries) {
            char firstChar = library.getBooks().getTitle().charAt(0);
            switch (firstChar) {
                case ('a'):
                case ('A'): {
                    arrayLists.get(0).add(library);
                    break;
                }
                case ('b'):
                case ('B'): {

                    arrayLists.get(1).add(library);
                    break;
                }
                case ('c'):
                case ('C'): {
                    arrayLists.get(2).add(library);
                    break;
                }
                case ('d'):
                case ('D'): {
                    arrayLists.get(3).add(library);
                    break;
                }
                case ('e'):
                case ('E'): {
                    arrayLists.get(4).add(library);
                    break;
                }
                case ('f'):
                case ('F'): {
                    arrayLists.get(5).add(library);
                    break;
                }
                case ('g'):
                case ('G'): {
                    arrayLists.get(6).add(library);
                    break;
                }
                case ('h'):
                case ('H'): {
                    arrayLists.get(7).add(library);
                    break;
                }
                case ('I'):
                case ('i'): {
                    arrayLists.get(8).add(library);
                    break;
                }
                case ('j'):
                case ('J'): {
                    arrayLists.get(9).add(library);
                    break;
                }
                case ('k'):
                case ('K'): {
                    arrayLists.get(10).add(library);
                    break;
                }
                case ('l'):
                case ('L'): {
                    arrayLists.get(11).add(library);
                    break;
                }
                case ('m'):
                case ('M'): {
                    arrayLists.get(12).add(library);
                    break;
                }
                case ('n'):
                case ('N'): {
                    arrayLists.get(13).add(library);
                    break;
                }
                case ('o'):
                case ('O'): {
                    arrayLists.get(14).add(library);
                    break;
                }
                case ('p'):
                case ('P'): {
                    arrayLists.get(15).add(library);
                    break;
                }
                case ('q'):
                case ('Q'): {
                    arrayLists.get(16).add(library);
                    break;
                }
                case ('r'):
                case ('R'): {
                    arrayLists.get(17).add(library);
                    break;
                }
                case ('s'):
                case ('S'): {
                    arrayLists.get(18).add(library);
                    break;
                }
                case ('t'):
                case ('T'): {
                    arrayLists.get(19).add(library);
                    break;
                }
                case ('u'):
                case ('U'): {
                    arrayLists.get(20).add(library);
                    break;
                }
                case ('v'):
                case ('V'): {
                    arrayLists.get(21).add(library);
                    break;
                }
                case ('W'):
                case ('w'): {
                    arrayLists.get(22).add(library);
                    break;
                }
                case ('x'):
                case ('X'): {
                    arrayLists.get(23).add(library);
                    break;
                }
                case ('y'):
                case ('Y'): {
                    arrayLists.get(24).add(library);
                    break;
                }
                case ('z'):
                case ('Z'): {
                    arrayLists.get(25).add(library);
                    break;
                }
                default: {
                    arrayLists.get(26).add(library);
                }
            }
        }

        for (ArrayList arrayList : arrayLists) {
            LibraryAdapter adapter = new LibraryAdapter(LibraryFragment.this.getActivity(), arrayList);
            adapterList.add(adapter);
        }

        for (Integer i = 0; i < adapterList.size(); i ++) {
            recyclerViews.get(i).setAdapter(adapterList.get(i));
            recyclerViews.get(i).setLayoutManager(new LinearLayoutManager(LibraryFragment.this.getActivity(), RecyclerView.VERTICAL, false));
        }

    }

    //Inflate A to Z RecyclerView
    private void InflateRecyclerView(View view) {
        recyclerViews = new ArrayList<>();
        RecyclerView rvA, rvB, rvC, rvD, rvE, rvF, rvG, rvH, rvI, rvJ, rvK, rvL, rvM, rvN, rvO, rvP, rvQ, rvR, rvS, rvT, rvU, rvV, rvW, rvX, rvY, rvZ, rvOther;
        rvA = (RecyclerView) view.findViewById(R.id.A_category_listview);
        recyclerViews.add(rvA);
        rvB = (RecyclerView) view.findViewById(R.id.B_category_listview);
        recyclerViews.add(rvB);
        rvC = (RecyclerView) view.findViewById(R.id.C_category_listview);
        recyclerViews.add(rvC);
        rvD = (RecyclerView) view.findViewById(R.id.D_category_listview);
        recyclerViews.add(rvD);
        rvE = (RecyclerView) view.findViewById(R.id.E_category_listview);
        recyclerViews.add(rvE);
        rvF = (RecyclerView) view.findViewById(R.id.F_category_listview);
        recyclerViews.add(rvF);
        rvG = (RecyclerView) view.findViewById(R.id.G_category_listview);
        recyclerViews.add(rvG);
        rvH = (RecyclerView) view.findViewById(R.id.H_category_listview);
        recyclerViews.add(rvH);
        rvI = (RecyclerView) view.findViewById(R.id.I_category_listview);
        recyclerViews.add(rvI);
        rvJ = (RecyclerView) view.findViewById(R.id.J_category_listview);
        recyclerViews.add(rvJ);
        rvK = (RecyclerView) view.findViewById(R.id.K_category_listview);
        recyclerViews.add(rvK);
        rvL = (RecyclerView) view.findViewById(R.id.L_category_listview);
        recyclerViews.add(rvL);
        rvM = (RecyclerView) view.findViewById(R.id.M_category_listview);
        recyclerViews.add(rvM);
        rvN = (RecyclerView) view.findViewById(R.id.N_category_listview);
        recyclerViews.add(rvN);
        rvO = (RecyclerView) view.findViewById(R.id.O_category_listview);
        recyclerViews.add(rvO);
        rvP = (RecyclerView) view.findViewById(R.id.P_category_listview);
        recyclerViews.add(rvP);
        rvQ = (RecyclerView) view.findViewById(R.id.Q_category_listview);
        recyclerViews.add(rvQ);
        rvR = (RecyclerView) view.findViewById(R.id.R_category_listview);
        recyclerViews.add(rvR);
        rvS = (RecyclerView) view.findViewById(R.id.S_category_listview);
        recyclerViews.add(rvS);
        rvT = (RecyclerView) view.findViewById(R.id.T_category_listview);
        recyclerViews.add(rvT);
        rvU = (RecyclerView) view.findViewById(R.id.U_category_listview);
        recyclerViews.add(rvU);
        rvV = (RecyclerView) view.findViewById(R.id.V_category_listview);
        recyclerViews.add(rvV);
        rvW = (RecyclerView) view.findViewById(R.id.W_category_listview);
        recyclerViews.add(rvW);
        rvX = (RecyclerView) view.findViewById(R.id.X_category_listview);
        recyclerViews.add(rvX);
        rvY = (RecyclerView) view.findViewById(R.id.Y_category_listview);
        recyclerViews.add(rvY);
        rvZ = (RecyclerView) view.findViewById(R.id.Z_category_listview);
        recyclerViews.add(rvZ);
        rvOther = (RecyclerView) view.findViewById(R.id.Other_category_listview);
        recyclerViews.add(rvOther);
    }

    private void emain() {
        ArrayList<ArrayList<Integer>> testarray = new ArrayList<ArrayList<Integer>> ();

        for (Integer i = 0; i < 3; i ++) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            testarray.add(arrayList);
        }

        for(Integer i = 0; i < 10; i ++) {
            if (i%2==0) {
                if (testarray.isEmpty()) {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    testarray.get(1).add(i);
                }
            }
        }
        for (ArrayList<Integer> i : testarray) {
            System.out.print(i);
        }
    }
}