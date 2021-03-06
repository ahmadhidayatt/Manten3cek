package com.example.asus.manten3m;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */

public class WoList extends Fragment {

    private static final String TAG = "WoList";

    private List<WoHolder> conversationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private WoAdapter conversation_adapter;

    public WoList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView =  inflater.inflate(R.layout.wo_list_fragment, container, false);
        recyclerView = mRootView.findViewById(R.id.recycler_view);

        conversationList = new ArrayList<>();
        conversation_adapter = new WoAdapter(getContext(),conversationList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(conversation_adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                WoHolder chat_position = conversationList.get(position);

                if (chat_position.getUnreadCounter() != 0) {
                    Log.e(TAG, "-------> masuk");
                    ContentValues cvalues = new ContentValues();
                    cvalues.put(WoHolder.FIELD_UNREAD_COUNTER, 0);
                    new WoDB(getContext()).updateRecord(cvalues, WoHolder.FIELD__ID + " = " +chat_position._id);
                    chat_position.unread_counter = 0;
                    conversationList.set(position, chat_position);
                    conversation_adapter.notifyItemChanged(position);
                }

                Toast.makeText(getActivity(), chat_position.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                if (chat_position.flag == DataGlobal.PRIVATE_MESSAGE) {
                    Intent intent = new Intent(getActivity(), PrivateChatList.class);
                    intent.putExtra("chat_group_message", chat_position._id);
                    intent.putExtra("name", chat_position.getTitle());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else if (chat_position.flag == DataGlobal.GROUP_MESSAGE) {
                    Intent intent = new Intent(getActivity(), GroupChatList.class);
                    intent.putExtra("chat_group_message", chat_position._id);
                    intent.putExtra("name", chat_position.getTitle());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                WoHolder chat_position = conversationList.get(position);
                Toast.makeText(getActivity(), chat_position.getTitle()+" on longclick!", Toast.LENGTH_SHORT).show();
            }
        }));
        prepareConversationData();
        return mRootView;
    }

    private void prepareConversationData() {
        ArrayList<WoHolder> arCh = new WoDB(getContext()).getRecords(null);
        for (WoHolder convHolder : arCh) {
            if (convHolder.flag == DataGlobal.PRIVATE_MESSAGE) {
                PrivateChatHolder privateChatHolder =  new PrivateChatDB(getContext()).getRecord(PrivateChatHolder.FIELD__ID + "=" + convHolder.chat_group_message);
                convHolder.setLastMessage(privateChatHolder.messages);
                convHolder.setDate(privateChatHolder.delivered);


                PersonHolder personHolder =  new PersonDB(getContext()).getRecord(PersonHolder.FIELD__ID + "=" + privateChatHolder.friend);
                convHolder.setTitle(personHolder.name);

            } else if (convHolder.flag == DataGlobal.GROUP_MESSAGE) {
                GroupChatHolder groupChatHolder = new GroupChatDB(getContext()).getRecord(PrivateChatHolder.FIELD__ID + "=" + convHolder.chat_group_message);
                Log.e(TAG, groupChatHolder.toCommValues() + "");
                convHolder.setLastMessage(groupChatHolder.messages);
                convHolder.setDate(groupChatHolder.delivered);
//
                GroupHolder groupHolder = new GroupDB(getContext()).getRecord(PrivateChatHolder.FIELD__ID + "=" + convHolder.chat_group_message);
                convHolder.setTitle(groupHolder.name);
            }

            conversationList.add(convHolder);
        }

//        WoHolder objConv = new WoHolder("Mad Max: Fury Road", "Action & Adventure", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Inside Out", "Animation, Kids & Family", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Star Wars: Episode VII - The Force Awakens", "Action", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Shaun the Sheep", "Animation", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("The Martian", "Science Fiction & Fantasy", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Mission: Impossible Rogue Nation", "Action", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Up", "Animation", "20.09");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Star Trek", "Science Fiction", "20.09");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("The LEGO Movie", "Animation", "20.14");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Iron Man", "Action & Adventure", "20.08");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Aliens", "Science Fiction", "19.86");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Chicken Run", "Animation", "20.00");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Back to the Future", "Science Fiction", "19.85");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Raiders of the Lost Ark", "Action & Adventure", "19.81");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Goldfinger", "Action & Adventure", "19.65");
//        conversationList.add(objConv);
//
//        objConv = new WoHolder("Guardians of the Galaxy", "Science Fiction & Fantasy", "20.14");
//        conversationList.add(objConv);

        conversation_adapter.notifyDataSetChanged();
    }
}
