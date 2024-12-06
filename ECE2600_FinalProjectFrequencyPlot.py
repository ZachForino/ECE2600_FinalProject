import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Read the CSV file
data = pd.read_csv('spectrum.csv')

# Extract the columns
frequency = data.iloc[:, 0]
level = data.iloc[:, 1]

# Plot the frequency spectrum with shaded area above the curve
plt.figure(figsize=(10, 6))
plt.fill_between(frequency, level, level.min(), color='indigo', alpha=0.4)
plt.xscale('log')
plt.xlim(50, 20000)
plt.xlabel('Frequency (Hz)', fontsize=20)
plt.ylabel('Level (dB)', fontsize=20)
plt.title('Frequency Spectrum Plot', fontsize=25)
plt.grid(True, which='both', linestyle='--', linewidth=0.5)

# Add ticks on the x-axis to mark each hundred
ticks = [50, 100, 200, 500, 1000, 2000, 4000, 10000, 20000]
plt.xticks(ticks, ticks, fontsize=16)
plt.yticks(fontsize=16)

plt.show()
