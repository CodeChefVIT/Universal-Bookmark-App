from django.shortcuts import render, redirect
from django.http import HttpResponse
from .serializers import(
    URLDataSerializer
)
from .models import(
    URLData
)
from rest_framework.parsers import JSONParser
from rest_framework.response import Response
from rest_framework import permissions
from rest_framework.views import APIView

# Create your views here.

def home(response):
    return render(response, "home.html", {})

class URLAdder(APIView):
    permission_classes = [permissions.IsAuthenticated]
    parser_classes = [JSONParser]

    def get(self, request):
        obj = URLData.objects.filter(user=request.user.id)
        serializer = URLDataSerializer(obj, many=True)
        return Response(serializer.data, status=200)
    
    def post(self, request):
        request.data['user'] = request.user.id
        obj = URLDataSerializer(data=request.data)
        if obj.is_valid():
            obj.save()
            return Response({
                "status" : "added",
            },status=201)

class URLModifier(APIView):
    permission_classes = [permissions.IsAuthenticated]
    parser_classes = [JSONParser]

    def post(self, request):
        try:    
            obj = URLData.objects.filter(id=request.data['id'])
            obj.update(url=request.data['url'])
            return Response(status=202)
        except:
            return Response(status=404)

class URLDelete(APIView):
    permission_classes = [permissions.IsAuthenticated]
    parser_classes = [JSONParser]

    def get(self, request, pk):
        try:    
            obj = URLData.objects.filter(id=pk)
            obj.delete()
            return Response({
                "status" : "deleted"
                },status=203)
        except:
            return Response(status=404)

class URLDeleteAll(APIView):
    permission_classes = [permissions.IsAuthenticated]
    parser_classes = [JSONParser]

    def get(self, request):
        try:
            URLData.objects.filter(user=request.user.id).delete()
            return Response(status=204)
        except:
            return Response(status=404)
