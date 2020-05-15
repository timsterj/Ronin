package com.timsterj.ronin.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.timsterj.ronin.adapters.holders.TitleViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class ItemSwipeCallback extends ItemTouchHelper.SimpleCallback {

    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;
    private List<ItemButtonAction> buttonList;
    private int swipePosition = -1;
    private int buttonWidth = 0;
    private float swipeThreshold = 0.5f;
    private Map<Integer, List<ItemButtonAction>> buttonBuffer;
    private Queue<Integer> removeQueue;

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for (ItemButtonAction item : buttonList) {
                if (item.onClick(e.getX(), e.getY())) {
                    break;
                }

            }

            return true;
        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (swipePosition < 0) {
                return false;
            }
            Point point = new Point((int) event.getRawX(), (int) event.getRawY());

            RecyclerView.ViewHolder swipeViewHolder = recyclerView.findViewHolderForAdapterPosition(swipePosition);

            if (swipeViewHolder instanceof TitleViewHolder || swipeViewHolder == null) {
                return false;
            }

            View swipedItem = swipeViewHolder.itemView;
            Rect rect = new Rect();
            swipedItem.getGlobalVisibleRect(rect);

            if (event.getAction() == MotionEvent.ACTION_DOWN ||
                    event.getAction() == MotionEvent.ACTION_UP ||
                    event.getAction() == MotionEvent.ACTION_MOVE
            ) {
                if (rect.top < point.y && rect.bottom > point.y) {
                    gestureDetector.onTouchEvent(event);
                } else {
                    removeQueue.add(swipePosition);
                    swipePosition = -1;
                    recoverSwipedItem();
                }

            }

            return false;
        }
    };

    private synchronized void recoverSwipedItem() {
        while (!removeQueue.isEmpty()) {
            int pos = removeQueue.poll();
            if (pos > -1) {
                recyclerView.getAdapter().notifyItemChanged(pos);
            }
        }

    }

    public ItemSwipeCallback(Context context, RecyclerView recyclerView, int buttonWidth) {
        super(0, ItemTouchHelper.LEFT);
        this.recyclerView = recyclerView;
        this.buttonList = new ArrayList<>();
        this.gestureDetector = new GestureDetector(context, gestureListener);
        this.recyclerView.setOnTouchListener(onTouchListener);
        this.buttonBuffer = new HashMap<>();
        this.buttonWidth = buttonWidth;

        removeQueue = new LinkedList<Integer>() {
            @Override
            public boolean add(Integer o) {

                if (contains(o)) {
                    return false;
                } else {
                    return super.add(o);
                }
            }
        };

        attachSwipe();

    }

    private void attachSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        if (viewHolder instanceof TitleViewHolder) {
            return;
        }

        if (swipePosition != pos) {
            removeQueue.add(swipePosition);
        }
        swipePosition = pos;

        if (buttonBuffer.containsKey(swipePosition)) {
            buttonList = buttonBuffer.get(swipePosition);
        } else {
            buttonList.clear();
        }
        buttonBuffer.clear();
        swipeThreshold = 0.5f * buttonList.size() * buttonWidth;
        recoverSwipedItem();

    }

    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return swipeThreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f * defaultValue;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return 5.0f * defaultValue;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int pos = viewHolder.getAdapterPosition();

        if (viewHolder instanceof TitleViewHolder) {
            return;
        }

        float translationX = dX;
        View itemView = viewHolder.itemView;

        if (pos < 0) {
            swipePosition = pos;
            return;
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                List<ItemButtonAction> buffer = new ArrayList<>();
                if (!buttonBuffer.containsKey(pos)) {
                    instantiateActionButton(viewHolder, buffer);
                    buttonBuffer.put(pos, buffer);

                } else {
                    buffer = buttonBuffer.get(pos);
                }

                translationX = dX * buffer.size() * buttonWidth / itemView.getWidth();

                drawButton(c, itemView, buffer, pos, translationX);

            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
    }

    private void drawButton(Canvas c, View itemView, List<ItemButtonAction> buffer, int pos, float translationX) {
        float right = itemView.getRight();
        float dButtonWidth = -1 * translationX / buffer.size();
        for (ItemButtonAction item : buffer) {
            float left = right - dButtonWidth;
            item.onDraw(c, new RectF(left, itemView.getTop(), right, itemView.getBottom()), pos);
            right = left;


        }
    }

     public abstract void instantiateActionButton(RecyclerView.ViewHolder viewHolder, List<ItemButtonAction> buffer);


}
