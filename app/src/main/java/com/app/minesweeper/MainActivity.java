package com.app.minesweeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_cells = findViewById(R.id.rv_cells);

        // 產生選取難度等級的對話框
//        new AlertDialog.Builder(MainActivity.this).setTitle(R.string.select_level)
//                .setItems(R.array.level, (dialogInterface, i) -> {
//                    // 傳入level參數，對話框消失
//                })
//                .show();
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = 9;
        ArrayList<Cell> cells = cellCreator.create();
        rv_cells.setAdapter(new MainAdapter(cells));
        rv_cells.setLayoutManager(new GridLayoutManager(this,9));

    }

    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

        private ArrayList<Cell> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView iv_cell;

            public ViewHolder(View view) {
                super(view);

                iv_cell = view.findViewById(R.id.iv_cell);
            }

            public ImageView getIv_cell() {
                return iv_cell;
            }
        }

        public MainAdapter(ArrayList<Cell> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cell_item, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getIv_cell().setImageResource(R.drawable.cell_shape);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

}