package com.example.sample.view

interface MovableBindingListController<T, VH: BindingListViewHolder<T>>: BindingListController<T, VH>, MovableListAdapter

class MovalbeBindingListAdapter<T, VH: BindingListViewHolder<T>>(
    controller: MovableBindingListController<T, VH>,
    source: BindingListDataSource<T>
): BindingListViewAdapter<T, VH>(controller, source), MovableListAdapter by controller