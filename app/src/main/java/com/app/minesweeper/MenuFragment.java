package com.app.minesweeper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.app.minesweeper.databinding.FragmentMenuBinding;

/**
 * 遊戲主選單 Fragment
 */
public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding = null;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater,container,false);

        // 取得根視圖
        View view = binding.getRoot();

        setSpinnerAdapter(R.array.players_array, binding.spPlayers);
        setSpinnerAdapter(R.array.sizes_array, binding.spSizes);
        setSpinnerAdapter(R.array.levels_array, binding.spLevels);
        binding.btStart.setOnClickListener(view1 -> {
            NavDirections action =
                    MenuFragmentDirections.actionMenuFragmentToGameFragment();
            Navigation.findNavController(view1).navigate(action);
        });

        return view;
    }

    private void setSpinnerAdapter(int textArrayResId, AppCompatSpinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                textArrayResId,R.layout.spinner_style);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
    }
}