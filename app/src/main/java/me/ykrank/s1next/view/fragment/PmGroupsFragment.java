package me.ykrank.s1next.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.ykrank.s1next.data.api.model.PmGroup;
import me.ykrank.s1next.data.api.model.collection.PmGroups;
import me.ykrank.s1next.data.api.model.wrapper.PmGroupsWrapper;
import me.ykrank.s1next.util.L;
import me.ykrank.s1next.util.MathUtil;
import me.ykrank.s1next.view.adapter.BaseRecyclerViewAdapter;
import me.ykrank.s1next.view.adapter.PmGroupsRecyclerViewAdapter;
import me.ykrank.s1next.widget.track.event.page.PageEndEvent;
import me.ykrank.s1next.widget.track.event.page.PageStartEvent;
import rx.Observable;


public final class PmGroupsFragment extends BaseLoadMoreRecycleViewFragment<PmGroupsWrapper> {

    public static final String TAG = PmGroupsFragment.class.getName();
    private PmGroupsRecyclerViewAdapter mRecyclerAdapter;

    public static PmGroupsFragment newInstance() {
        PmGroupsFragment fragment = new PmGroupsFragment();
        return fragment;
    }

    @Override
    BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        return mRecyclerAdapter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.leaveMsg("PmGroupsFragment");

        RecyclerView recyclerView = getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerAdapter = new PmGroupsRecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        trackAgent.post(new PageStartEvent(getContext(), "私信列表-PmGroupsFragment"));
    }

    @Override
    public void onPause() {
        trackAgent.post(new PageEndEvent(getContext(), "私信列表-PmGroupsFragment"));
        super.onPause();
    }

    @Override
    Observable<PmGroupsWrapper> getSourceObservable(int pageNum) {
        return mS1Service.getPmGroups(pageNum);
    }

    @Override
    void onNext(PmGroupsWrapper data) {
        super.onNext(data);
        PmGroups pmGroups = data.getPmGroups();
        if (pmGroups.getPmGroupList() != null) {
            mRecyclerAdapter.diffNewDataSet(pmGroups.getPmGroupList(), false);
            // update total page
            setTotalPages(MathUtil.divide(pmGroups.getTotal(), pmGroups.getPmPerPage()));
        }
    }

    @Override
    PmGroupsWrapper appendNewData(@Nullable PmGroupsWrapper oldData, @NonNull PmGroupsWrapper newData) {
        if (oldData != null) {
            List<PmGroup> oldPmGroups = oldData.getPmGroups().getPmGroupList();
            List<PmGroup> newPmGroups = newData.getPmGroups().getPmGroupList();
            if (newPmGroups == null) {
                newPmGroups = new ArrayList<>();
            }
            if (oldPmGroups != null) {
                newPmGroups.addAll(0, oldPmGroups);
            }
        }
        return newData;
    }
}