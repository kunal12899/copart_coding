
from scipy.cluster.vq import kmeans, vq, whiten
import numpy as np
from numpy import vstack,array
from pylab import plot,show

import csv

if __name__ == "__main__":
    # clusters to find out number of nearest location using unsupervised algorithm
    K = 2
    yard_loc_arr = []
    geo_location = []
    with open('zip_code_states.csv', 'rb') as f:
        reader = csv.reader(f)
        for row in reader:
            yard_loc_arr.append([float(x) for x in row[1:]])
            geo_location.append([row[0],row[1]])

    data = vstack(yard_loc_arr)
    geo_location_info = vstack(geo_location)

    # normalization
    data = whiten(data)


    # computing K-Means with K (clusters)
    centroids, distortion = kmeans(data,2)
    print "distortion = " + str(distortion)

    # assign each sample to a cluster
    idx,_ = vq(data,centroids)

    # sample geo location
    sample_loc =[34.343,87.44]

    # find nearest location using kmeans algorithm

    # some plotting using numpy's logical indexing
    plot(data[idx==0,0], data[idx==0,1],'ob',
         data[idx==1,0], data[idx==1,1],'or',
         data[idx==2,0], data[idx==2,1],'og')

    print geo_location_info
    print data
    distance_array =[]

    for i in range(K):
        result_names = geo_location_info[idx==i, 0]
        distance_array = np.sum(((result_names[0].values-sample_loc[0]) - (result_names[1].values)-sample_loc[0]) ** 2)

    print(" Top 2 nearest location")

    print(distance_array.argsort()[:2])
