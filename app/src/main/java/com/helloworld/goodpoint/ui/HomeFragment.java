package com.helloworld.goodpoint.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.adapter.MyExpandableListAdapter;
import com.helloworld.goodpoint.pojo.LostItem;
import com.helloworld.goodpoint.pojo.LostObject;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.retrofit.ApiClient;
import com.helloworld.goodpoint.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> objects; //To link group list with child list
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    TextView Daily_msg;
    TextView Hi_msg;
    List<LostObject> listObj;
    LostItem list1;
    String LossesObjects[];
    //   String FindingsItems[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Daily_msg = v.findViewById(R.id.daily_message);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            Daily_msg.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Daily_msg.setText("Good Afternoon");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            Daily_msg.setText("Good Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            Daily_msg.setText("Good Night");
        }

        Hi_msg = v.findViewById(R.id.hi_message);
        Hi_msg.setText("Hi, " + User.getUser().getUsername());

        // Inflate the layout for this fragment
        // FindingsItems = getHomeFounds();
        LossesObjects = getHomeLosts();
        createGroupList();
        createObjects(LossesObjects);

        expandableListView = v.findViewById(R.id.expanded_menu);
        expandableListAdapter = new MyExpandableListAdapter(getActivity(), groupList, objects); //getActivity
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                return true;
            }
        });

        return v;
    }


    private void createObjects(String[] itemsL) {

        String[] FindingsItems = {"Mobile HUAWEI", "Black wallet", "Child", "ID card"};

        objects = new HashMap<String, List<String>>();
        for (String group : groupList) {
            if (group.equals(getString(R.string.Losses))) {
                loadChild(itemsL);
            } else if (group.equals(getString(R.string.Founds)))
                loadChild(FindingsItems);
            objects.put(group, childList);
        }
    }

    private void loadChild(String[] AllObjects) {
        childList = new ArrayList<>();
        if (AllObjects != null) {
            for (int i = 0; i < AllObjects.length; i++)
                childList.add(AllObjects[i]);
        } else
            childList.add("b");
    }


    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add(getString(R.string.Losses));
        groupList.add(getString(R.string.Founds));
    }

    /*  public String[] getHomeFounds() {

          ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getContext()).getNGROKLink()).create(ApiInterface.class);
          Call<List<FoundItem>> call = apiInterface.getHomeFounds_i(User.getUser().getId());
          call.enqueue(new Callback<List<FoundItem>>() {
              @Override
              public void onResponse(Call<List<FoundItem>> call, Response<List<FoundItem>> response) {
                  try {
                      JSONObject jsonObject = new JSONObject(response.body().toString());
                      String id = jsonObject.getString("id");

                      Call<List<FoundItem>> call2 = apiInterface.getFItem(id);
                      call2.enqueue(new Callback<List<FoundItem>>() {
                          @Override
                          public void onResponse(Call<List<FoundItem>> call, Response<List<FoundItem>> response) {

                          }

                          @Override
                          public void onFailure(Call<List<FoundItem>> call, Throwable t) {
                              Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                          }
                      });

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              }


              @Override
              public void onFailure(Call<List<FoundItem>> call, Throwable t) {
                  Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

              }
          });
          Log.d("e", "message=" + FindingsItems);
          return FindingsItems;
      }
  */
    public String[] getHomeLosts() {

        ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getContext()).getNGROKLink()).create(ApiInterface.class);
        Call<List<LostObject>> call = apiInterface.getHomeLosts_i(User.getUser().getId());
        call.enqueue(new Callback<List<LostObject>>() {
            @Override
            public void onResponse(Call<List<LostObject>> call, Response<List<LostObject>> response) {

                listObj = response.body();
                int id[] = new int[listObj.size()];
                for (int i = 0; i < listObj.size(); i++) {
                    id[i] = listObj.get(i).getId();
                    Log.d("e", "id=" + id[i]);
                }
                Log.d("e", "list=" + id[0]);
                Call<LostItem> call2 = apiInterface.getLostItem(1);
                call2.enqueue(new Callback<LostItem>() {
                    @Override
                    public void onResponse(Call<LostItem> call, Response<LostItem> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Item is posted.", Toast.LENGTH_SHORT).show();
                            list1 = response.body();
                            if (list1 != null) {
                                LossesObjects[0] += list1.getType() + " " + list1.getBrand();
                            } else
                                Toast.makeText(getContext(), "There is no items !", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), "The item is not posted.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<LostItem> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<LostObject>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        Log.d("e", "message=" + LossesObjects);
        return LossesObjects;
    }


}