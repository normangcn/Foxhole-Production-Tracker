package org.ng.fhb;

    public class EmbedUtils {
        public static String generateProgressBar(int percentage) {
            int totalBars = 20; // Total segments in the progress bar
            int filledBars = (percentage * totalBars) / 100;
            int emptyBars = totalBars - filledBars;

            StringBuilder progressBar = new StringBuilder();
            progressBar.append("`[");
            progressBar.append("█".repeat(filledBars)); // Filled segments
            progressBar.append("-".repeat(emptyBars));  // Empty segments
            progressBar.append("]`");

            return progressBar.toString();
        }

}
