package me.ykrank.s1next.view.internal;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.ykrank.s1next.databinding.FragmentBaseBinding;
import me.ykrank.s1next.viewmodel.LoadingViewModel;

public final class LoadingViewModelBindingDelegateBaseImpl
        implements LoadingViewModelBindingDelegate {

    private final FragmentBaseBinding binding;

    public LoadingViewModelBindingDelegateBaseImpl(FragmentBaseBinding binding) {
        this.binding = binding;
    }

    @Override
    public View getRootView() {
        return binding.getRoot();
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return binding.swipeRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return binding.recyclerView;
    }

    @Override
    public void setLoadingViewModel(LoadingViewModel loadingViewModel) {
        binding.setLoadingViewModel(loadingViewModel);
    }
}
