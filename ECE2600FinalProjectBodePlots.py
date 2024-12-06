import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Read the CSV file
data = pd.read_csv('ECE2600FilterDesign1.csv')

# Extract the columns
frequency = data.iloc[:, 0]
magnitude1 = data.iloc[:, 1]
magnitude2 = data.iloc[:, 2]

# Transform the y-axis values to 20 * log10(y)
magnitude1_db = 20 * np.log10(magnitude1)
magnitude2_db = 20 * np.log10(magnitude2)

# Plot the Bode plots
plt.figure(figsize=(10, 6))
plt.plot(frequency, magnitude1_db, label='High-Pass', color='blue')
plt.plot(frequency, magnitude2_db, label='Low-Pass', color='red')
plt.xscale('log')
plt.xlim(10, 20000)
plt.ylim(-120, 20)
plt.xlabel('Frequency (Hz)', fontsize=20)
plt.ylabel('Magnitude (dB)', fontsize=20)
plt.title('Bode Plots', fontsize=25)
plt.grid(True, which='both', linestyle='--', linewidth=0.5)

# Add vertical lines and labels 
plt.axvline(x=281.525, color='pink', linestyle='-', linewidth=1, label='Low-Pass Cutoff = 281.525Hz') 
plt.axvline(x=494.948, color='skyblue', linestyle='-', linewidth=1, label='High-Pass Cutoff = 494.948') 


# Add ticks on the x-axis to mark each hundred
ticks = [10, 100, 200, 300, 500, 1000, 2000, 4000, 20000]
plt.xticks(ticks, ticks, fontsize=16)
plt.yticks(fontsize=12)

plt.legend()
plt.show()

