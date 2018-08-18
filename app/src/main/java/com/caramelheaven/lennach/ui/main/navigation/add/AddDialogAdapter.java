package com.caramelheaven.lennach.ui.main.navigation.add;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caramelheaven.lennach.R;
import com.caramelheaven.lennach.datasource.model.BoardNavModel;
import com.caramelheaven.lennach.ui.base.AdapterMethods;
import com.caramelheaven.lennach.utils.myOnItemClickListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

public class AddDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterMethods<BoardNavModel> {

    private List<BoardNavModel> boardNavList;
    private Set<BoardNavModel> boardUnique;
    private Set<Integer> selectedItems;

    private mySpesialOnItemClickListener mySpesialOnItemClickListener;

    public AddDialogAdapter(List<BoardNavModel> boardNavList) {
        this.boardNavList = boardNavList;
        boardUnique = new LinkedHashSet<>();
        selectedItems = new LinkedHashSet<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choose_board, viewGroup, false);
        return new BoardVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BoardVH boardVH = (BoardVH) viewHolder;
        boardVH.tvBoardName.setText(boardNavList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return boardNavList.size();
    }

    @Override
    public void updateAdapter(List models) {
        boardUnique.addAll(models);
        if (boardNavList.size() > 0)
            boardNavList.clear();
        boardNavList.addAll(boardUnique);
        notifyDataSetChanged();
    }

    public void setMySpesialOnItemClickListener(com.caramelheaven.lennach.ui.main.navigation.add.mySpesialOnItemClickListener mySpesialOnItemClickListener) {
        this.mySpesialOnItemClickListener = mySpesialOnItemClickListener;
    }

    @Override
    public BoardNavModel getItemByPosition(int position) {
        return boardNavList.get(position);
    }

    @Override
    public List<BoardNavModel> getItems() {
        return boardNavList;
    }

    public List<BoardNavModel> getSelectedItems() {
        List<BoardNavModel> selectedBoards = new ArrayList<>();
        for (Integer position : selectedItems) {
            selectedBoards.add(boardNavList.get(position));
        }
        return selectedBoards;
    }

    public Set<Integer> getSelectedPositions() {
        return selectedItems;
    }

    public void setSelectedItems(Set<Integer> models) {
        selectedItems.addAll(models);
    }

    private class BoardVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvBoardName;
        ImageView ivImageBoard;
        CardView cardView;

        boolean checkable = false;

        public BoardVH(@NonNull View itemView) {
            super(itemView);
            tvBoardName = itemView.findViewById(R.id.tv_name_board);
            ivImageBoard = itemView.findViewById(R.id.iv_board);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            if (selectedItems.contains(getAdapterPosition())) {
                checkable = false;
                selectedItems.remove(getAdapterPosition());
                Timber.d("selected contains");
                cardView.setCardBackgroundColor(cardView.getContext().getResources().getColor(R.color.colorWhite));
            } else {
                Timber.d("white");
                checkable = true;
                selectedItems.add(getAdapterPosition());
                cardView.setCardBackgroundColor(cardView.getContext().getResources().getColor(R.color.colorSelected));
            }
            mySpesialOnItemClickListener.onItemClick(getAdapterPosition(), checkable);
        }
    }
}