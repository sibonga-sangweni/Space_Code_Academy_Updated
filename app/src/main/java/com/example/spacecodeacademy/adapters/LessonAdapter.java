package com.example.spacecodeacademy.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecodeacademy.R;
import com.example.spacecodeacademy.models.Lesson;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private List<Lesson> lessons;
    private OnLessonClickListener listener;
    private String topicName;

    public interface OnLessonClickListener {
        void onLessonClick(int position, Lesson lesson);
    }

    public LessonAdapter(List<Lesson> lessons, OnLessonClickListener listener, String topicName) {
        this.lessons = lessons;
        this.listener = listener;
        this.topicName = topicName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.titleText.setText(lesson.getTitle());
        holder.contentText.setText(lesson.getContent());

        // Get completion status
        SharedPreferences prefs = holder.itemView.getContext().getSharedPreferences("lesson_progress", Context.MODE_PRIVATE);
        String key = topicName + "_" + lesson.getTitle();
        boolean isCompleted = prefs.getBoolean(key, false);

        // Check if previous lesson is completed (for lock status)
        boolean isPreviousCompleted = true;
        if (position > 0) {
            Lesson prevLesson = lessons.get(position - 1);
            String prevKey = topicName + "_" + prevLesson.getTitle();
            isPreviousCompleted = prefs.getBoolean(prevKey, false);
        }

        // FINAL variables for lambda (FIX THE ERROR)
        final boolean finalIsCompleted = isCompleted;
        final boolean finalIsPreviousCompleted = isPreviousCompleted;
        final int finalPosition = position;

        // Set icon and styling based on completion status
        if (isCompleted) {
            // COMPLETED LESSON - Green checkmark
            holder.iconContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.completed_green));
            holder.lessonIcon.setText("✅");
            holder.lessonIcon.setTextSize(18);
            holder.lessonStatus.setText("Completed");
            holder.lessonStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.completed_green_text));
            holder.lessonStatus.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.status_badge_completed));
            holder.iconContainer.setAlpha(1f);
            holder.cardView.setAlpha(0.9f);
        }
        else if (!isPreviousCompleted && position > 0) {
            // LOCKED LESSON - Gray lock (previous lesson not completed)
            holder.iconContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.locked_gray));
            holder.lessonIcon.setText("🔒");
            holder.lessonIcon.setTextSize(18);
            holder.lessonStatus.setText("Locked");
            holder.lessonStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.locked_gray_text));
            holder.lessonStatus.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.status_badge_locked));
            holder.iconContainer.setAlpha(0.6f);
            holder.cardView.setAlpha(0.7f);
        }
        else {
            // AVAILABLE LESSON - Blue book icon
            holder.iconContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.available_blue));
            holder.lessonIcon.setText("📘");
            holder.lessonIcon.setTextSize(18);
            holder.lessonStatus.setText("Start");
            holder.lessonStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.available_blue_text));
            holder.lessonStatus.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.status_badge_available));
            holder.iconContainer.setAlpha(1f);
            holder.cardView.setAlpha(1f);
        }

        // Set click listener on the card (only if not locked)
        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                // Don't allow clicking on locked lessons - using final variables
                if (!finalIsCompleted && !finalIsPreviousCompleted && finalPosition > 0) {
                    android.widget.Toast.makeText(holder.itemView.getContext(),
                            "Complete the previous lesson first!",
                            android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onLessonClick(finalPosition, lesson);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LinearLayout iconContainer;
        TextView lessonIcon;
        TextView titleText, contentText;
        TextView lessonStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.lessonCard);
            iconContainer = itemView.findViewById(R.id.lessonIconContainer);
            lessonIcon = itemView.findViewById(R.id.lessonIcon);
            titleText = itemView.findViewById(R.id.lessonTitle);
            contentText = itemView.findViewById(R.id.lessonContent);
            lessonStatus = itemView.findViewById(R.id.lessonStatus);
        }
    }
}