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
from rest_framework.parsers import MultiPartParser, FormParser

# Create your views here.
class LostObjectView(generics.ListCreateAPIView):
    queryset = LostObject.objects.all()
    serializer_class = LostObjectSerializer


class LostObjectDetailsView(generics.RetrieveUpdateDestroyAPIView):
    queryset = LostObject.objects.all()
    serializer_class = LostObjectSerializer


class LostItemView(generics.GenericAPIView):
    queryset = LostItem.objects.all()
    serializer_class = LostItemSerializer
    parser_classes = (MultiPartParser, FormParser)

    def create(self, request):
        print(request.data)
        serializer = self.get_serializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class LostItemDetailsView(generics.RetrieveUpdateDestroyAPIView):
    queryset = LostItem.objects.all()
    serializer_class = LostItemSerializer


class LostPersonView(generics.ListCreateAPIView):
    http_method_names = ['post']
    queryset = LostPerson.objects.all()
    serializer_class = LostPersonSerializer


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
    http_method_names = ['post']
    queryset = FoundPerson.objects.all()
    serializer_class = FoundPersonSerializer


class FoundPersonImageView(generics.ListCreateAPIView):
    queryset = FoundPersonImage.objects.all()
    serializer_class = FoundPersonImageSerializer


class MapView(generics.ListAPIView):
    queryset = FoundItem.objects.select_related('id')
    serializer_class = MapSerializer
