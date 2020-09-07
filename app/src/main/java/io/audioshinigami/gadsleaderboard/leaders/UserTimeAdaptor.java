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

package io.audioshinigami.gadsleaderboard.leaders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import io.audioshinigami.data_gads.data.UserTime;
import io.audioshinigami.gadsleaderboard.BR;
import io.audioshinigami.gadsleaderboard.GlideApp;
import io.audioshinigami.gadsleaderboard.databinding.HourItemBinding;

public class UserTimeAdaptor extends ListAdapter<UserTime, UserTimeAdaptor.UserTimeViewHolder> {


    private static final DiffUtil.ItemCallback<UserTime> callback = new DiffUtil.ItemCallback<UserTime>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserTime oldItem, @NonNull UserTime newItem) {
            return oldItem.uid == newItem.uid;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserTime oldItem, @NonNull UserTime newItem) {
            return oldItem.uid == newItem.uid && oldItem.name.equals(newItem.name) && oldItem.country.equals(newItem.country);
        }
    };

    protected UserTimeAdaptor(){
        super(callback);
    }

    @NonNull
    @Override
    public UserTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserTimeViewHolder holder, int position) {
        holder.bind( getItem(position));
    }

    class UserTimeViewHolder extends RecyclerView.ViewHolder {

        private final HourItemBinding binding;

        public UserTimeViewHolder(@NonNull HourItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserTime userTime){

            GlideApp.with(binding.getRoot().getContext())
                    .load(userTime.badgeUrl)
                    .centerCrop()
                    .into(binding.userImage);

            binding.setVariable(BR.user, userTime);
            binding.executePendingBindings();
        }

        public void clear(){
            binding.unbind();
        }

    }

    private UserTimeViewHolder createViewHolder(ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HourItemBinding binding = HourItemBinding.inflate(layoutInflater, parent, false);

        return new UserTimeViewHolder(binding);
    }
}
