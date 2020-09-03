/*
 * MIT License
 *
 * Copyright (c) 2020 David O.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.feature_iqlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.feature_iqlist.databinding.RviewIqItemBinding;

public class UserIqAdaptor extends ListAdapter<UserIq , UserIqAdaptor.UserIqViewHolder> {

    private static final DiffUtil.ItemCallback<UserIq> callback = new DiffUtil.ItemCallback<UserIq>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserIq oldItem, @NonNull UserIq newItem) {
            return oldItem.uid == newItem.uid;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserIq oldItem, @NonNull UserIq newItem) {
            return oldItem.uid == newItem.uid && oldItem.name.equals(newItem.name) && oldItem.country.equals(newItem.country);
        }
    };

    protected UserIqAdaptor() {
        super(callback);
    }

    @NonNull
    @Override
    public UserIqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserIqViewHolder holder, int position) {
        holder.bind( getItem(position));
    }

    class UserIqViewHolder extends RecyclerView.ViewHolder {

        private final RviewIqItemBinding binding;

        public UserIqViewHolder(@NonNull RviewIqItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserIq userIq){

            Glide.with(binding.getRoot().getContext())
                    .load(userIq.badgeUrl)
                    .centerCrop()
                    .into(binding.userImage);

            binding.setVariable(BR.user, userIq);
            binding.executePendingBindings();
        }

        public void clear(){
            binding.unbind();
        }
    }

    private UserIqViewHolder createViewHolder(ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RviewIqItemBinding binding = RviewIqItemBinding.inflate(layoutInflater, parent, false);

        return new UserIqViewHolder(binding);
    }
}
