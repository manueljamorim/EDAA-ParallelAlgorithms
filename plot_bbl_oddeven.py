import pandas as pd
import matplotlib.pyplot as plt


data_values = {
    "Elements": ["10'000", "100'000", "1'000'000", "10'000'000", "100'000'000"],
    "Sequential Bubble Sort Time": [131, 18053, 1869443, None, None],
    "Parallel Bubble Sort Time": [1316, 13548, 243988, None, None]
}

merge_sort_data = pd.DataFrame(data_values)

merge_sort_data = merge_sort_data.replace({None: pd.NA})

merge_sort_data["Time Ratio"] = (
    merge_sort_data["Sequential Bubble Sort Time"]
    / merge_sort_data["Parallel Bubble Sort Time"]
)
merge_sort_data["Time Ratio"] = (
    merge_sort_data["Time Ratio"].replace(float("inf"), 0).replace(0, float("nan"))
)  # Replace zero ratios with NaN


colors = [
    "green" if seq < par else "red"
    for seq, par in zip(
        merge_sort_data["Sequential Bubble Sort Time"],
        merge_sort_data["Parallel Bubble Sort Time"],
    )
]


plt.figure(figsize=(12, 8))
x = range(len(merge_sort_data["Elements"]))  

rects = plt.bar(
    x,
    merge_sort_data["Time Ratio"],
    width=0.35,
    color=colors,
    label="Time Ratio (Sequential Bubble Sort / Parallel Odd Even Transposition Sort)",
)
plt.ylabel("Time Ratio (Sequential Bubble Sort / Parallel Odd Even Transposition Sort)")
plt.title("Time Ratio of Sequential Bubble Sort to Parallel Odd Even Transposition Sort with Timings")
plt.xticks(x, merge_sort_data["Elements"])
plt.xlabel("Number of Elements")
plt.legend()

# Adjust y-axis scale by 10% to fit labels
ymax = merge_sort_data["Time Ratio"].max() * 1.2
plt.ylim(
    0, ymax if not pd.isna(ymax) else 1
)  # Handle cases where all values might be NaN

def autolabel_with_timings(rects):
    for i, rect in enumerate(rects):
        height = rect.get_height()
        label = (
            f"{height:.2f}\nBBL: {merge_sort_data['Sequential Bubble Sort Time'].iloc[i]}ms\nOES: {merge_sort_data['Parallel Bubble Sort Time'].iloc[i]}ms"
            if not pd.isna(height)
            else ""
        )
        plt.annotate(
            label,
            xy=(
                rect.get_x() + rect.get_width() / 2,
                height if not pd.isna(height) else 0,
            ),
            xytext=(0, 3),  # 3 points vertical offset
            textcoords="offset points",
            ha="center",
            va="bottom",
        )

autolabel_with_timings(rects)
plt.show()