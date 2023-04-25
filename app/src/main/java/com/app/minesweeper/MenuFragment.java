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
    private String player;
    private String size;
    private String level;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater,container,false);

        // 取得根視圖
        View view = binding.getRoot();

        // 設定 Spinner 的選項內容
        setSpinnerAdapter(R.array.players_array, binding.spPlayers);
        setSpinnerAdapter(R.array.sizes_array, binding.spSizes);
        setSpinnerAdapter(R.array.levels_array, binding.spLevels);

        binding.btStart.setOnClickListener(btView -> {
            // 取得 spinner 選取項目文字
            player = binding.spPlayers.getSelectedItem().toString();
            size = binding.spSizes.getSelectedItem().toString();
            level = binding.spLevels.getSelectedItem().toString();
            NavDirections action =
                    MenuFragmentDirections.actionMenuFragmentToGameFragment(player,size,level); // 導航並傳遞參數
            Navigation.findNavController(btView).navigate(action);
        });
        return view;
    }

    /**
     * 設定 Spinner 的選項內容
     * @param textArrayResId 傳入的文字列表資源
     * @param spinner binding.spinnerName
     */
    private void setSpinnerAdapter(int textArrayResId, AppCompatSpinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                textArrayResId,R.layout.spinner_style);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
    }
}