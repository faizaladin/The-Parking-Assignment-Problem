import math

parkingSpots = [[1,2], [3,5], [4,9], [7,10]]
student1 = [[4,5,5], [6,9,3], [8,5,4]]

spot = (0,0)

def distance(list1, list2):
    x = (list1[0]-list2[0]) ** 2
    y = (list1[1] - list2[1]) ** 2
    return math.sqrt(x + y)


def minDist(parkingSpots, student):
    temptotal = 0
    total = 10 ** 5
    for i in range(len(parkingSpots)):
        for x in range(len(student)):
            temptotal += (distance(parkingSpots[i], student[x]) * student[x][2])

        if temptotal < total:
            total = temptotal
            temptotal = 0
            spot = parkingSpots[i]

    print(spot, total)
            

minDist(parkingSpots, student1)