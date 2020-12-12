package com.nurbk.ps.assignment12.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nurbk.ps.assignment12.R;
import com.nurbk.ps.assignment12.adapter.PhotosAdapter;
import com.nurbk.ps.assignment12.model.Root;
import com.nurbk.ps.assignment12.network.NetworkRetrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private PhotosAdapter photosAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Group group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchView searchView = (SearchView) requireView().findViewById(R.id.searchView);
        searchView.setOnCloseListener(() -> true);

        recyclerView = requireView().findViewById(R.id.rcDataSearch);
        progressBar = requireView().findViewById(R.id.progressBar);
        group = requireView().findViewById(R.id.groupLoad);

        requireView().findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().onBackPressed());
        requireView().findViewById(R.id.btnReload).setOnClickListener(v -> {
            group.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            loadData(searchView.getQuery().toString());
        });

        progressBar.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                loadData(newText);
                return false;
            }
        });
        photosAdapter = new PhotosAdapter(new ArrayList(), photo -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("Photo", photo);
            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(bundle);

            requireActivity().
                    getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ContainerSearchFragment,
                            fragment, "").addToBackStack("").commit();
        });
        recyclerView.setAdapter(photosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


    }


    private void loadData(String query) {
        NetworkRetrofit.getInstance()
                .getPhotosApiInterface()
                .getMethodData("flickr.photos.search", null, query)
                .enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(Call<Root> call, Response<Root> response) {
                        if (response.isSuccessful() && response.body().getStat().equals("ok"))
                            try {
                                photosAdapter.setPhotoList(response.body().getPhotos().getPhoto());
                            } catch (Exception e) {

                            }
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<Root> call, Throwable t) {
                        group.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
