from django.shortcuts import render
from rest_framework_simplejwt.tokens import RefreshToken, TokenError
from rest_framework import serializers
from django.http import HttpResponse
from .models import LostObject, LostItem, LostPerson, LostPersonImage
from rest_framework import status
from .serializers import *
from rest_framework import generics
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.reverse import reverse
from rest_framework import status
from rest_framework import viewsets
from rest_framework import permissions

# Create your views here.
class LostObjectView(generics.ListCreateAPIView):
    queryset = LostObject.objects.all()
    serializer_class = LostObjectSerializer


class LostObjectDetailsView(generics.RetrieveUpdateDestroyAPIView):
    queryset = LostObject.objects.all()
    serializer_class = LostObjectSerializer


class LostItemView(generics.ListCreateAPIView):
    queryset = LostItem.objects.all()
    serializer_class = LostItemSerializer

class LostItemDetailsView(generics.RetrieveUpdateDestroyAPIView):
    queryset = LostItem.objects.all()
    serializer_class = LostItemSerializer


class LostPersonView(generics.ListCreateAPIView):
    queryset = LostPerson.objects.all()
    serializer_class = LostPersonSerializer
    def post(self, request):
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_204_NO_CONTENT)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class LostPersonDetailsView(generics.RetrieveUpdateDestroyAPIView):
    queryset = LostPerson.objects.all()
    serializer_class = LostPersonSerializer


class LostPersonImageView(generics.ListCreateAPIView):
    queryset = LostPersonImage.objects.all()
    serializer_class = LostPersonImageSerializer

class LostPersonImageDetailsView(generics.RetrieveUpdateDestroyAPIView):
    queryset = LostPersonImage.objects.all()
    serializer_class = LostPersonImageSerializer


class FoundObjectView(generics.ListCreateAPIView):
    queryset = FoundObject.objects.all()
    serializer_class = FoundObjectSerializer

class FoundObjectDetalisView(generics.RetrieveUpdateDestroyAPIView):
    queryset = FoundObject.objects.all()
    serializer_class = FoundObjectSerializer


class FoundItemView(generics.ListCreateAPIView):
    queryset = FoundItem.objects.all()
    serializer_class = FoundItemSerializer

class FoundItemDetailsView(generics.RetrieveUpdateDestroyAPIView):
    queryset = FoundItem.objects.all()
    serializer_class = FoundItemSerializer


class FoundPersonView(generics.ListCreateAPIView):
    queryset = FoundPerson.objects.all()
    serializer_class = FoundPersonSerializer


class FoundPersonImageView(generics.ListCreateAPIView):
    queryset = FoundPersonImage.objects.all()
    serializer_class = FoundPersonImageSerializer


class LostObject_cityView(generics.ListAPIView):
    serializer_class = LostObjectSerializer

    def get_queryset(self):
        obj = self.kwargs['city']
        return LostObject.objects.filter(city=obj)


class MapView(generics.ListAPIView):
    queryset = FoundItem.objects.select_related('id')
    serializer_class = MapSerializer